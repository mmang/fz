package com.mangmang.fz.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by wangzhenguang on 2018/1/11.
 */
open abstract class SupportFragment : Fragment(), ISupportFragment {

    private val delegate = SupportFragmentDelegate(this)
    private lateinit var mActivity: FragmentActivity


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        delegate.onAttach(activity!!)
        mActivity = delegate.activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        delegate.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        delegate.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        delegate.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        delegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        delegate.onHiddenChanged(hidden)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        delegate.setUserVisibleHint(isVisibleToUser)
    }

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return delegate
    }

    override fun setFragmentResult(resultCode: Int, bundle: Bundle?) {
        delegate.setFragmentResult(resultCode, bundle)
    }

    override fun onSupportInvisible() {
        delegate.onSupportInvisible()
    }

    override fun onNewBundle(args: Bundle?) {
        delegate.onNewBundle(args)
    }

    override fun extraTransaction(): ExtraTransaction {
        return delegate.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return delegate.onCreateFragmentAnimator()
    }

    override fun enqueueAction(runnable: Runnable?) {
        delegate.enqueueAction(runnable)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        delegate.onFragmentResult(requestCode, resultCode, data)
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        delegate.fragmentAnimator = fragmentAnimator
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        delegate.onLazyInitView(savedInstanceState)
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return delegate.fragmentAnimator
    }

    override fun isSupportVisible(): Boolean {
        return delegate.isSupportVisible
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        delegate.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onSupportVisible() {
        delegate.onSupportVisible()
    }

    override fun onBackPressedSupport(): Boolean {
        return delegate.onBackPressedSupport()
    }


    override fun putNewBundle(newBundle: Bundle?) {
        delegate.putNewBundle(newBundle)
    }

    override fun post(runnable: Runnable?) {
        delegate.post(runnable)
    }


}