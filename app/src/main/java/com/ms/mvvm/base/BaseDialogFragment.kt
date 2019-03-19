package com.ms.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ms.mvvm.MyApp
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment() {

    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    @Inject
    lateinit var mRCApplication: IApp


    protected lateinit var mSubscription: CompositeDisposable

    private var viewModel: IViewModel? = null

    abstract fun getLayoutId(): Int

    abstract fun createViewModel(activityComponent: IViewModelComponent): IViewModel

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
        viewModel = createViewModel(activity?.getActivityComponent()!!)
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


}