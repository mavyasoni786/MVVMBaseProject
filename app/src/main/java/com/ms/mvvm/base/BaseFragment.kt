package com.ms.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ms.mvvm.MyApp
import com.ms.mvvm.eventbus.IBaseEvent
import com.ms.mvvm.eventbus.IEventBus
import com.ms.mvvm.eventbus.IEventBusListener
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), IEventBusListener {

    private var viewModel: IViewModel? = null

    protected lateinit var mSubscription: CompositeDisposable

    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    @Inject
    lateinit var mRCApplication: IApp

    @Inject
    lateinit var mEventBus: IEventBus

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(baseFragment: BaseFragment, activityComponent: IViewModelComponent): IViewModel

    abstract fun onFragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = activity?.application as MyApp
        val platformLifetimeComponent = application.getPlatform()
            .getApplicationLifetimeComponent()
        platformLifetimeComponent.inject(this)

        mSubscription = CompositeDisposable()
        val activity = activity as BaseActivity?
        viewModel = createViewModel(this, activity?.getActivityComponent()!!)
        viewModel!!.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onFragmentCreateView(inflater, container, savedInstanceState)
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

    override fun onEvent(baseEvent: IBaseEvent) {

    }
}