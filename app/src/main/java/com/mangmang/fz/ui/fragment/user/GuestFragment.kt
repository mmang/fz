package com.mangmang.fz.ui.fragment.user

import android.support.v7.widget.LinearLayoutManager
import com.happyfi.lelerong.base.BaseFragment
import com.happyfi.lelerong.base.BaseInjectorFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.user.GuestAdapter
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.applySchedulers
import kotlinx.android.synthetic.main.layout_refresh.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
class GuestFragment : BaseInjectorFragment() {
    var pageIndex = 1

    val adapter by lazy { GuestAdapter(mutableListOf()) }

    @Inject
    lateinit var apiservice: ApiService


    override fun getLayoutResources(): Int {
        return R.layout.layout_refresh
    }


    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    override fun initData() {
        val uid = arguments?.getString(Constants.USER_ID)
        uid?.let {
            apiservice.getBeVisited(uid, pageIndex.toString())
                    .applySchedulers()
                    .requestCallBack {
                        adapter.addData(it.data!!.list)
                    }
        }
    }

}