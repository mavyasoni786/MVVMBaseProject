package com.ms.mvvm.base

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ms.mvvm.MyApp
import com.ms.mvvm.base.factory.PlatformObjectsThatNeedAppWideLifetime
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.injection.modules.ActivityViewModelModule
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.utils.PermissionUtils
import com.ms.mvvm.utils.Trace
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    lateinit var viewModelComponent: IViewModelComponent

    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    @Inject
    lateinit var mRCApplication: IApp


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
        if (viewModel != null)
            viewModel!!.onResume()
    }

    override fun onPause() {
        super.onPause()
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
        if (viewModel != null)
            viewModel!!.onDestroy()
    }
}