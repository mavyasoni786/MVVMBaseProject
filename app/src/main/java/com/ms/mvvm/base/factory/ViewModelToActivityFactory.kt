package com.ms.mvvm.base.factory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.base.BaseFragment
import com.ms.mvvm.base.BaseFragmentManager
import com.ms.mvvm.base.BaseFragmentState
import com.ms.mvvm.interfaces.IViewModelToActivityFactory
import java.lang.ref.WeakReference

class ViewModelToActivityFactory(weakActivity: BaseActivity) : IViewModelToActivityFactory {


    private var weakActivity: WeakReference<BaseActivity> = WeakReference(weakActivity)

    override fun startActivity(activityClass: Class<out Activity>) {
        val activity = weakActivity.get()
        if (activity != null && !activity.isFinishing) {
            activity.startActivity(Intent(activity, activityClass))
        }
    }

    override fun startActivityWithBundle(activityClass: Class<out Activity>, bundle: Bundle) {
        val activity = weakActivity.get()
        if (activity != null && !activity.isFinishing) {
            val mIntent = Intent(activity, activityClass)
            mIntent.putExtras(bundle)
            activity.startActivity(mIntent)
        }
    }

    override fun startActivityAndFinishThis(activityClass: Class<out Activity>) {
        val activity = weakActivity.get()
        if (activity != null && !activity.isFinishing) {
            val intent = Intent(activity, activityClass)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    override fun finish() {
        val activity = weakActivity.get()
        if (activity != null && !activity.isFinishing) {
            activity.finish()
        }
    }

    override fun onBackPressed() {
        val activity = weakActivity.get()
        if (activity != null && !activity.isFinishing) {
            activity.onBackPressed()
        }
    }

    override fun getActivity(): AppCompatActivity {
        return weakActivity.get()!!
    }

    override fun getSupportFragmentManager(): FragmentManager {
        val activity = weakActivity.get()
        return if (activity != null && !activity.isFinishing) {
            activity.supportFragmentManager
        } else null!!
    }

    override fun getFragmentManager(): FragmentManager {
        val activity = weakActivity.get()
        return if (activity != null && !activity.isFinishing) {
            activity.supportFragmentManager
        } else null!!
    }

    override fun attachFragment(containerId: Int, isAddToBackStack: Boolean, baseFragment: Fragment) {
        val ft = getSupportFragmentManager().beginTransaction()
        ft.replace(containerId, baseFragment, baseFragment.javaClass.simpleName)
        if (isAddToBackStack)
            ft.addToBackStack(baseFragment.javaClass.simpleName)
        ft.commit()
    }

    override fun addFragment(
        containerId: Int,
        isAddToBackStack: Boolean,
        previousFragment: Fragment,
        fragment: Fragment
    ) {
        val ft = getSupportFragmentManager().beginTransaction()
        ft.hide(previousFragment)
        ft.replace(containerId, fragment, fragment.javaClass.simpleName)
        if (isAddToBackStack)
            ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }

    override fun getBaseFragmentManager(): BaseFragmentManager {
        val activity = weakActivity.get()
        return if (activity != null && !activity.isFinishing) {
            activity.mBaseFragmentManager!!
        } else null!!
    }


    override fun addFragment(mBaseFragmentState: BaseFragmentState, keys: Any?, isAnimation: Boolean) {
        getBaseFragmentManager().addFragment<BaseFragment>(mBaseFragmentState, keys, isAnimation)
    }

    override fun replaceFragment(mBaseFragmentState: BaseFragmentState, keys: Any?, isAnimation: Boolean) {
        getBaseFragmentManager().replaceFragment<BaseFragment>(mBaseFragmentState, keys, isAnimation)
    }

}