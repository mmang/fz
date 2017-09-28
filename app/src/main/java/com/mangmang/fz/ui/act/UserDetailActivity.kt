package com.mangmang.fz.ui.act

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.mangmang.fz.R
import com.mangmang.fz.base.BaseCommonActivity
import com.mangmang.fz.bean.UserDetail
import com.mangmang.fz.ui.fragment.DynamicFragment
import com.mangmang.fz.ui.fragment.LogFragment
import com.mangmang.fz.ui.fragment.PhotoFragment
import com.mangmang.fz.ui.fragment.user.HistoryFragment
import com.mangmang.fz.ui.fragment.user.UserAlbumFragment
import com.mangmang.fz.ui.fragment.user.UserProfileFragment
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * Created by mangmang on 2017/9/21.
 */
class UserDetailActivity : BaseCommonActivity() {
    lateinit var tabTitls: Array<String>

    var userId: String? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_user_detail
    }

    override fun initDatas() {
        userId = intent.getStringExtra(Constants.USER_ID)
        if (checkUserId()) return
        tabTitls = resources.getStringArray(R.array.user_detail_tabs)
        val userProfileFragment = UserProfileFragment()
        val userAlbumFragment = UserAlbumFragment()
        val bundle = Bundle()
        bundle.putString(Constants.USER_ID, userId)
        userProfileFragment.arguments = bundle
        userAlbumFragment.arguments = bundle
        val fragments = listOf(userProfileFragment, userAlbumFragment, LogFragment(), DynamicFragment(), HistoryFragment())
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return tabTitls.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return tabTitls[position]
            }
        }
        tablayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = tabTitls.size

//        list.layoutManager = LinearLayoutManager(this)
//        list.setAdapter(object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//                var tv = holder.itemView as TextView
//                tv.setText(position.toString())
//                tv.textSize = 30f
//            }
//
//
//            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
//                return object : RecyclerView.ViewHolder(TextView(this@UserDetailActivity)) {}
//            }
//
//            override fun getItemCount(): Int {
//                return 100
//            }
//
//        })
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
        Glide.with(this)
                .load(userdetail.data.data.avatar)
                .into(ivHead)

        //设置名字
        tvName.text = userdetail.data.data.username

        //设置粉丝 和关注数
        tvFans.text = userdetail.data.data.fansCount
        tvFollow.text = userdetail.data.data.followsCount

    }


}
