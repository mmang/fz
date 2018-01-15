package com.mangmang.fz.ui.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.happyfi.lelerong.base.BaseFragment
import com.mangmang.fz.R
import com.mangmang.fz.ui.fragment.user.GuestFragment
import com.mangmang.fz.ui.fragment.user.UserAlbumFragment
import com.mangmang.fz.ui.fragment.user.UserProfileFragment
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * Created by mangmang on 2017/9/19.
 */
class MineFragment : BaseFragment() {

    lateinit var tabTitls: Array<String>

    var userId: String? = null
    override fun getLayoutResources(): Int {
        return R.layout.fragment_user_detail
    }


    override fun initView() {
        this.rootView?.background = ColorDrawable(resources.getColor(R.color.lineColor))
    }

    override fun initData() {
        userId = arguments.getString(Constants.USER_ID)
        userId ?: let {
            showToast("未知用户")
            return
        }
        tabTitls = resources.getStringArray(R.array.user_detail_tabs)

        val userProfileFragment = UserProfileFragment()
        val userAlbumFragment = UserAlbumFragment()
        val logFragment = LogFragment()
        val dynamicFragment = DynamicFragment()
        val guestFragment = GuestFragment()

        val bundle = Bundle()
        bundle.putString(Constants.USER_ID, userId)
        userProfileFragment.arguments = bundle
        userAlbumFragment.arguments = bundle
        logFragment.arguments = bundle
        dynamicFragment.arguments = bundle
        guestFragment.arguments = bundle

        val fragments = listOf<Fragment>(userProfileFragment, userAlbumFragment, logFragment, dynamicFragment, guestFragment)
        viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
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
        viewPager.offscreenPageLimit = 1
        viewPager.setCurrentItem(0, false)
        tablayout.setupWithViewPager(viewPager, true)
    }

}