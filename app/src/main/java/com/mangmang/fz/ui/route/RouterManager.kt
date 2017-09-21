package com.mangmang.fz.ui.route

import android.app.Activity
import com.mangmang.fz.ui.act.LoginAtivity
import com.mangmang.fz.ui.act.MainActivity

/**
 * Created by mangmang on 2017/9/19.
 *  跳转导航管理
 */
object RouterManager {

    /**
     * 跳入登录页面
     */
    fun navLogin(context: Activity, clazz: Class<*>) {
        Router.newIntent(context)
                .to(clazz)
                .launch()
    }
}