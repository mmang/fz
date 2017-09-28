package com.mangmang.fz.ui.fragment.user

import android.support.v7.widget.LinearLayoutManager
import com.mangmang.fz.R
import com.mangmang.fz.adapter.UserDetailAdapter
import com.mangmang.fz.bean.UserDetail
import com.mangmang.fz.bean.UserDetailField
import com.mangmang.fz.ui.contract.UserProfileContract
import com.mangmang.fz.ui.fragment.HasPFragment
import com.mangmang.fz.ui.presenter.UserProfileP
import com.mangmang.fz.utils.Constants
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * Created by mangmang on 2017/9/22.
 */
class UserProfileFragment : HasPFragment<UserProfileP>(), UserProfileContract.V {

    override fun setUserProfile(it: UserDetail, fileds: MutableList<UserDetailField>) {
        recyclerView.adapter = UserDetailAdapter(R.layout.item_detail, fileds)
    }

    override fun initView() {
        val userId = arguments.getString(Constants.USER_ID)
        recyclerView.layoutManager = LinearLayoutManager(context)
        presenter.loadUserProfile(userId)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_user_profile
    }
}