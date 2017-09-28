package com.mangmang.fz.ui.act

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mangmang.fz.*
import com.mangmang.fz.base.BaseCommonActivity
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.ui.route.RouterManager
import com.mangmang.fz.utils.MD5Util
import com.mangmang.fz.utils.UserManager
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import com.wzg.readbook.ui.contract.WelcomeContract
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class WelcomeAct : BaseCommonActivity() {

    @Inject
    lateinit var api: ApiService

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initDatas() {
        SystemBarUtils.fullScreen(window)
//
        val account = UserManager.getAccount()
        if (!TextUtils.isEmpty(account.name) and !TextUtils.isEmpty(account.password)) {
            val queryMap = HashMap<String, String>()
            val params = HashMap<String, String>()
            queryMap.put("a", "login")
            queryMap.put("m", "account/account")
            params.put("authorization", account.password)
            params.put("username", account.name)
            api.login(queryMap, params)
                    .applySchedulers()
                    .bindUntilEvent(this, ActivityEvent.DESTROY)
                    .subscribe({
                        if (1 == it.account_status) {
                            UserManager.storeAccount(account.name, account.password, it.auth_token)
                            Router.newIntent(this@WelcomeAct)
                                    .to(MainActivity::class.java)
                                    .launch()
                        } else {
                            Router.newIntent(this@WelcomeAct)
                                    .to(LoginAtivity::class.java)
                                    .launch()
                        }
                        UserManager.user = it
                        this@WelcomeAct.finish()
                    }, {
                        showToast(it.message + "")
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