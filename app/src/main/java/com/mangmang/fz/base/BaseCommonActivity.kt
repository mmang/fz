package com.mangmang.fz.base

import android.os.Build
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import com.mangmang.fz.DialogManager
import com.mangmang.fz.R
import com.mangmang.fz.utils.ActivityManagerUtil
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/18.
 *
 *  没有注解p的基类
 *
 */
abstract class BaseCommonActivity : DaggerAppCompatActivity(), LifecycleProvider<ActivityEvent>,
        HasFragmentInjector, HasSupportFragmentInjector {

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return frameworkFragmentInjector
    }


    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycle.bind(lifecycleSubject)
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        this.lifecycleSubject.onNext(ActivityEvent.CREATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        setContentView(getLayoutId())
        ActivityManagerUtil.pushOneActivity(this)
        initFragment(savedInstanceState)
        initDatas()
        initlistener()
    }

    override fun setContentView(layoutResID: Int) {
        val inflater = LayoutInflater.from(this)
        var rootView = inflater.inflate(R.layout.activity_base, null)
        val content = inflater.inflate(layoutResID, null)
        var flytContent = rootView.findViewById<FrameLayout>(R.id.flytContent)
        flytContent.addView(content)
        super.setContentView(rootView)
    }


    protected abstract fun getLayoutId(): Int
    protected abstract fun initDatas()
    protected abstract fun initlistener()


    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
        ActivityManagerUtil.popOneActivity(this)
        DialogManager.dissLoadDialog()
    }

    protected open fun initFragment(savedInstanceState: Bundle?){

    }
}