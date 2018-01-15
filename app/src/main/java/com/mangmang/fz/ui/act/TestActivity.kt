package com.mangmang.fz.ui.act

import android.content.Context
import com.happyfi.lelerong.base.BaseHasPActivity
import com.mangmang.fz.R
import com.mangmang.fz.ui.presenter.TestP
import com.mangmang.fz.utils.showToast
import com.wzg.readbook.ui.contract.TestContract

/**
 * Created by mangmang on 2017/9/14.
 */
class TestActivity : BaseHasPActivity<TestP>(), TestContract.V {
    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContext(): Context {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initlistener() {

    }

    override fun testV(string: String) {
        showToast(string)
    }

    override fun initDatas() {
        presenter.testP()
    }


    override fun showError(msg: String?) {
    }

    override fun complete() {
    }

}