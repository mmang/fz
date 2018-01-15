package com.mangmang.fz.ui.act

import android.text.TextUtils
import android.view.View
import com.happyfi.lelerong.base.BaseCommonActivity
import com.mangmang.fz.R
import com.mangmang.fz.SystemBarUtils
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.ui.route.RouterManager
import com.mangmang.fz.utils.UserManager
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_base.*
import java.util.concurrent.TimeUnit

/**
 * Created by mangmang on 2017/9/14.
 */
class WelcomeAct : BaseCommonActivity() {
    override fun initView() {
        titleBar.visibility = View.GONE
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    val list = mutableListOf<String>()
    override fun initDatas() {
        SystemBarUtils.fullScreen(window)
        val account = UserManager.getAccount()
        if (!TextUtils.isEmpty(account.name) and !TextUtils.isEmpty(account.password)) {
            val queryMap = HashMap<String, String>()
            val params = HashMap<String, String>()
            queryMap.put("a", "login")
            queryMap.put("m", "account/account")
            params.put("authorization", account.password)
            params.put("username", account.name)
            apiService.login(queryMap, params)
                    .applySchedulers()
                    .bindUntilEvent(this, ActivityEvent.DESTROY)
                    .requestCallBack({
                        //储存token
                        UserManager.storeAccount(account.name, account.password, it.data!!.token)
                        Router.newIntent(this@WelcomeAct)
                                .to(MainActivity::class.java)
                                .launch()
                        UserManager.user = it.data
                        this@WelcomeAct.finish()
                    }, { _, msg ->
                        showToast(msg)
                        Router.newIntent(this@WelcomeAct)
                                .to(LoginAtivity::class.java)
                                .launch()
                    })
        } else {
            Observable.timer(1, TimeUnit.SECONDS)
                    .applySchedulers()
                    .bindUntilEvent(this, ActivityEvent.DESTROY)
                    .subscribe({
                        RouterManager.navLogin(this, LoginAtivity::class.java)
                    }, {
                        showToast("请求失败")
                        RouterManager.navLogin(this, LoginAtivity::class.java)
                    })
        }
    }

    override fun initlistener() {
    }

}