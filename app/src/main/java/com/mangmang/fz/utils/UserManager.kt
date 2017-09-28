package com.mangmang.fz.utils

import android.util.Log
import com.mangmang.fz.FZApplication
import com.mangmang.fz.SPUtils
import com.mangmang.fz.bean.Account
import com.mangmang.fz.bean.User

/**
 * Created by mangmang on 2017/9/20.
 * 获取用户信息
 */
object UserManager {

    var user: User? = null
    private val spName = "account"
    private val kName = "userName"
    private val kPassword = "password"
    private val kToken = "token"
    fun storeAccount(name: String, password: String, token: String) {
        val spUtils = SPUtils.getInstance(FZApplication.instance, spName)
        spUtils.put(kName, name)
        spUtils.put(kPassword, password)
        spUtils.put(kToken, token)
        Log.d("account", "$name    $password")
    }


    fun getAccount(): com.mangmang.fz.bean.Account {
        val spUtils = SPUtils.getInstance(FZApplication.instance, spName)
        val name = spUtils.getString(kName)
        val password = spUtils.getString(kPassword)
        spUtils.put(kToken, "")

        Log.d("account", "$name   ------         $password")
        return Account(name, password)
    }

    fun getToken(): String? {
        val spUtils = SPUtils.getInstance(FZApplication.instance, spName)
        return spUtils.getString(kToken)
    }


}