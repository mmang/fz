package com.mangmang.fz.ui.act

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mangmang.fz.BaseActivity
import com.mangmang.fz.R
import com.mangmang.fz.SystemBarUtils
import com.mangmang.fz.ui.fragment.*
import com.mangmang.fz.ui.presenter.MianP
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mangmang on 2017/9/14.
 */
class MainActivity : BaseActivity<MianP>(), View.OnClickListener {

    lateinit var dynamicFragment: DynamicFragment
    lateinit var photoFragment: PhotoFragment
    lateinit var logFragment: LogFragment
    lateinit var nearbyFragment: NearbyFragment
    lateinit var mineFragment: MineFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun showError(msg: String) {
    }

    override fun complete() {
    }

    override fun initlistener() {
        setRadioButton()
    }


    override fun initDatas() {
        SystemBarUtils.fullScreen(window)
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
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


    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_dynamic -> {
                rb_dynamic.isChecked = true
                rb_dynamic.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(dynamicFragment)
                        .hide(photoFragment)
                        .hide(mineFragment)
                        .hide(logFragment)
                        .hide(nearbyFragment)
                        .commit()
            }
            R.id.rb_photo -> {
                rb_photo.isChecked = true
                rb_photo.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(photoFragment)
                        .hide(dynamicFragment)
                        .hide(mineFragment)
                        .hide(logFragment)
                        .hide(nearbyFragment)
                        .commit()

            }
            R.id.rb_log -> {
                rb_log.isChecked = true
                rb_log.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(logFragment)
                        .hide(dynamicFragment)
                        .hide(mineFragment)
                        .hide(photoFragment)
                        .hide(nearbyFragment)
                        .commit()
            }
            R.id.rb_nearby -> {
                rb_nearby.isChecked = true
                rb_nearby.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(nearbyFragment)
                        .hide(dynamicFragment)
                        .hide(mineFragment)
                        .hide(logFragment)
                        .hide(photoFragment)
                        .commit()
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(dynamicFragment)
                        .hide(photoFragment)
                        .hide(logFragment)
                        .hide(nearbyFragment)
                        .commit()
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


    override fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is DynamicFragment) {
                    dynamicFragment = item
                }
                if (item is PhotoFragment) {
                    photoFragment = item
                }
                if (item is LogFragment) {
                    logFragment = item
                }
                if (item is NearbyFragment) {
                    nearbyFragment = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            dynamicFragment = DynamicFragment()
            mineFragment = MineFragment()
            logFragment = LogFragment()
            nearbyFragment = NearbyFragment()
            photoFragment = PhotoFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.container, dynamicFragment)
            fragmentTrans.add(R.id.container, photoFragment)
            fragmentTrans.add(R.id.container, logFragment)
            fragmentTrans.add(R.id.container, nearbyFragment)
            fragmentTrans.add(R.id.container, mineFragment)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(dynamicFragment)
                .hide(photoFragment)
                .hide(mineFragment)
                .hide(logFragment)
                .hide(nearbyFragment)
                .commit()
    }
}