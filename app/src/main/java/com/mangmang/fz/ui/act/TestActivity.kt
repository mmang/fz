package com.mangmang.fz.ui.act

import com.mangmang.fz.BaseActivity
import com.mangmang.fz.R
import com.mangmang.fz.ui.presenter.TestP
import com.mangmang.fz.utils.showToast
import com.wzg.readbook.ui.contract.TestContract

/**
 * Created by mangmang on 2017/9/14.
 */
class TestActivity : BaseActivity<TestP>(), TestContract.V {
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


    override fun showError(msg: String) {
    }

    override fun complete() {
    }

}