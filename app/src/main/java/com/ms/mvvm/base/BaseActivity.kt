package com.ms.mvvm.base

import android.annotation.TargetApi
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.ms.mvvm.MyApp
import com.ms.mvvm.base.factory.PlatformObjectsThatNeedAppWideLifetime
import com.ms.mvvm.eventbus.IBaseEvent
import com.ms.mvvm.eventbus.IEventBus
import com.ms.mvvm.eventbus.IEventBusListener
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.injection.modules.ActivityViewModelModule
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.manager.configuration.IConfigurationManager
import com.ms.mvvm.manager.configuration.IConfigurationManagerEvent
import com.ms.mvvm.utils.PermissionUtils
import com.ms.mvvm.utils.Trace
import io.reactivex.disposables.CompositeDisposable
import java.security.MessageDigest
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), IEventBusListener {

    lateinit var viewModelComponent: IViewModelComponent

    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    @Inject
    lateinit var mRCApplication: IApp

    protected lateinit var mSubscription: CompositeDisposable

    @Inject
    lateinit var mEventBus: IEventBus

    @Inject
    lateinit var mConfigurationManager: IConfigurationManager

    private val mObjects = PlatformObjectsThatNeedAppWideLifetime()

    private var viewModel: IViewModel? = null

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): IViewModel

    abstract fun onCreate()
    var permissionUtils: PermissionUtils? = null
        private set
    var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionUtils = PermissionUtils(this)
        this.savedInstanceState = savedInstanceState
        val application = application as MyApp
        val platformLifetimeComponent = application.getPlatform()
            .getApplicationLifetimeComponent()
        platformLifetimeComponent.inject(this)
        mObjects.inject(platformLifetimeComponent)

        mSubscription = CompositeDisposable()
        mSubscription.add(mEventBus.subscribe(this))

        viewModelComponent = mRCApplication
            .getApplicationModule()
            .getComponentFactory()
            .createActivityViewModelComponent(ActivityViewModelModule(this))

        viewModel = createViewModel()
        onCreate()
    }

    fun getActivityComponent(): IViewModelComponent {
        return this.viewModelComponent
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onEvent(baseEvent: IBaseEvent) {
        if (baseEvent.isEventOfPassedType(IConfigurationManagerEvent::class.java)) run {
            val event = baseEvent as IConfigurationManagerEvent
            if (event.getEventType() === IConfigurationManagerEvent.EventType.RequestUserForPermissions) {
                val params = event.getPermissionRequestParams()
                requestForPermission(params.getPermissionName(), params.getPermissionRationale(), params.getRequestId())
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestForPermission(permission: String, permissionRationale: Int, requestId: Int) {
        requestPermissions(arrayOf(permission), requestId)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.size != permissions.size) {
            Trace.error(
                "grant results length %d permission results length = %d",
                grantResults.size, permissions.size
            )
        }

//        for (i in permissions.indices) {
//            mConfigurationManager.onPermissionRequestResults(
//                    permissions[i],
//                    requestCode,
//                    grantResults[i] == PackageManager.PERMISSION_GRANTED
//            )
//        }
        permissionUtils!!.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)

    }

    override fun onStart() {
        super.onStart()
        if (viewModel != null)
            viewModel!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mSubscription.add(mEventBus.subscribe(this))
        if (viewModel != null)
            viewModel!!.onResume()
    }

    override fun onPause() {
        super.onPause()

        mSubscription.clear()
        if (viewModel != null)
            viewModel!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        if (viewModel != null)
            viewModel!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSubscription.dispose()
        if (viewModel != null)
            viewModel!!.onDestroy()
    }


    protected fun printHashKey() {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Trace.logFuncWithMessage("KeyHash: %s", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: Exception) {
            Trace.throwable(e)
        }
    }
}