package com.mangmang.fz.ui.act

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.happyfi.lelerong.base.BaseCommonActivity
import com.mangmang.fz.R
import com.mangmang.fz.bean.UserDetail
import com.mangmang.fz.ui.fragment.MineFragment
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.GlideUtil
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * Created by mangmang on 2017/9/21.
 */
class UserDetailActivity : BaseCommonActivity() {


    lateinit var tabTitls: Array<String>

    var userId: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_user_detail
    }

    override fun initView() {
        titleBar.visibility = View.GONE
    }

    override fun initDatas() {
        userId = intent.getStringExtra(Constants.USER_ID)
        if (checkUserId()) return
        val mineFragment = MineFragment()
        val bundle = Bundle()
        bundle.putString(Constants.USER_ID, userId)
        mineFragment.arguments = bundle;
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, mineFragment)
                .commit()
    }

    private fun checkUserId(): Boolean {
        if (TextUtils.isEmpty(userId)) {
            showToast("未知用户id")
            finish()
            return true
        }
        return false
    }


    override fun initlistener() {
    }


    fun setUserProfile(userdetail: UserDetail) {
        //设置头像
        GlideUtil.load(this, userdetail.data.data.avatar)
                .into(ivHead)

        //设置名字
        tvName.text = userdetail.data.data.username

        //设置粉丝 和关注数
        tvFans.text = userdetail.data.data.fansCount
        tvFollow.text = userdetail.data.data.followsCount

    }


}
