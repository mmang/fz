package com.happyfi.lelerong.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mangmang.fz.DialogManager
import com.mangmang.fz.R
import com.mangmang.fz.utils.ActivityManagerUtil
import com.mangmang.fz.utils.StatusBarUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_base.view.*
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by mangmang on 2017/10/11.
 * 普通的基类 没有p层  没有注入
 */
abstract class BaseActivity : SupportActivity(), LifecycleProvider<ActivityEvent> {

    protected abstract fun getLayoutId(): Int
    protected abstract fun initDatas()
    protected abstract fun initView()
    protected abstract fun initlistener()

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
        super.onCreate(savedInstanceState)
        this.lifecycleSubject.onNext(ActivityEvent.CREATE)
        setContentView(getLayoutId())
        ActivityManagerUtil.pushOneActivity(this)
        initFragment(savedInstanceState)
        initView()
        initlistener()
        initDatas()
    }


    override fun setContentView(layoutResID: Int) {
        val inflater = LayoutInflater.from(this)
        var rootView = inflater.inflate(R.layout.activity_base, null)
        val content = inflater.inflate(layoutResID, null)
        var flytContent = rootView.findViewById<ViewGroup>(R.id.flytContent)
        flytContent.addView(content)
        super.setContentView(rootView)
        setStatuBar()
        titleBar.setNavigationOnClickListener { finish() }

    }


    open fun setStatuBar() {
        StatusBarUtils.with(this) //设置状态栏颜色
//               .setColor(resources.getColor(R.color.textColorPrimary))
                .setDrawable(resources.getDrawable(R.drawable.page_bg))
                .init()
    }

    protected open fun initFragment(savedInstanceState: Bundle?) {

    }


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
        ActivityManagerUtil.popOneActivity(this)
        DialogManager.dissLoadDialog()
        super.onDestroy()
    }

    fun setTitleBarVisbility(vis: Int) {
        titleBar.visibility = vis
        titleUnderLine.visibility = vis
    }

    fun getTitleBar(): Toolbar {
        return titleBar
    }

}