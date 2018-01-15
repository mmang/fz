package com.mangmang.fz.ui.act

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import com.happyfi.lelerong.base.BaseHasPActivity
import com.mangmang.fz.R
import com.mangmang.fz.SystemBarUtils
import com.mangmang.fz.base.SupportFragment
import com.mangmang.fz.ui.fragment.*
import com.mangmang.fz.ui.presenter.MianP
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.StatusBarUtils
import com.mangmang.fz.utils.UserManager
import com.wzg.readbook.ui.contract.MainContract
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mangmang on 2017/9/14.
 */
class MainActivity : BaseHasPActivity<MianP>(), View.OnClickListener, MainContract.V {


    private val mFragments = arrayOfNulls<SupportFragment>(5)
    private var currentPosition = 0

    override fun testV() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun showError(msg: String?) {
    }

    override fun complete() {
    }

    override fun initlistener() {
        setRadioButton()
    }

    override fun initView() {
        tvTitle.setText("赞")
        titleBar.setNavigationIcon(ColorDrawable(Color.TRANSPARENT))
        titleBar.setNavigationOnClickListener { }
    }

    override fun initDatas() {
    }

    private fun setRadioButton() {
        rb_dynamic.isChecked = true
        rb_dynamic.setTextColor(resources.getColor(R.color.black))
        rb_dynamic.setOnClickListener(this)
        rb_photo.setOnClickListener(this)
        rb_log.setOnClickListener(this)
        rb_nearby.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        val bundle = Bundle()
        bundle.putString(Constants.USER_ID, UserManager.user?.data?.uid)
        if (savedInstanceState == null) {
            mFragments[0] = DynamicFragment()
            mFragments[1] = PhotoFragment()
            mFragments[2] = LogFragment()
            mFragments[3] = NearbyFragment()
            mFragments[4] = MineFragment()
            supportDelegate.loadMultipleRootFragment(R.id.container, currentPosition,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]
            )
        } else {
            mFragments[0] = findFragment(DynamicFragment::class.java)
            mFragments[1] = findFragment(PhotoFragment::class.java)
            mFragments[2] = findFragment(LogFragment::class.java)
            mFragments[3] = findFragment(NearbyFragment::class.java)
            mFragments[4] = findFragment(MineFragment::class.java)
        }
        mFragments[4]?.arguments = bundle
    }

    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_dynamic -> {
                rb_dynamic.isChecked = true
                rb_dynamic.setTextColor(resources.getColor(R.color.black))
                supportDelegate.showHideFragment(mFragments[0], mFragments[currentPosition])
                currentPosition = 0
            }
            R.id.rb_photo -> {
                rb_photo.isChecked = true
                rb_photo.setTextColor(resources.getColor(R.color.black))
                supportDelegate.showHideFragment(mFragments[1], mFragments[currentPosition])
                currentPosition = 1
            }
            R.id.rb_log -> {
                rb_log.isChecked = true
                rb_log.setTextColor(resources.getColor(R.color.black))
                supportDelegate.showHideFragment(mFragments[2], mFragments[currentPosition])
                currentPosition = 2

            }
            R.id.rb_nearby -> {
                rb_nearby.isChecked = true
                rb_nearby.setTextColor(resources.getColor(R.color.black))
                supportDelegate.showHideFragment(mFragments[3], mFragments[currentPosition])
                currentPosition = 3

            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportDelegate.showHideFragment(mFragments[4], mFragments[currentPosition])
                currentPosition = 4
            }
        }

    }


    private fun clearState() {
        rg_root.clearCheck()
        rb_dynamic.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_photo.setTextColor(resources.getColor(R.color.gray))
        rb_nearby.setTextColor(resources.getColor(R.color.gray))
        rb_log.setTextColor(resources.getColor(R.color.gray))
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}