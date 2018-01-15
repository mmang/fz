package com.mangmang.fz.ui.act

import android.text.TextUtils
import com.happyfi.lelerong.base.BaseHasPActivity
import com.mangmang.fz.DialogManager
import com.mangmang.fz.R
import com.mangmang.fz.ui.contract.LoginContract
import com.mangmang.fz.ui.presenter.LoginP
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by mangmang on 2017/9/18.
 */
class LoginAtivity : BaseHasPActivity<LoginP>(), LoginContract.V {
    override fun initView() {

    }

    override fun loginScuess() {
        showToast("登录成功")
        Router.newIntent(this)
                .to(MainActivity::class.java)
                .launch()
    }

    override fun showError(msg: String?) {
        showToast(msg)
    }

    override fun loginFail() {
        DialogManager.dissLoadDialog()
    }

    override fun complete() {
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initlistener() {
        btnLogin.setOnClickListener({
            if (!TextUtils.isEmpty(etUserName.text) and !TextUtils.isEmpty(etPassword.text)) {
                presenter.login(etUserName.text.toString(), etPassword.text.toString())
            } else {
                showToast("用户名和密码不能为空")
            }
        })
        btnReg.setOnClickListener({

        })
    }


    override fun initDatas() {
    }


}