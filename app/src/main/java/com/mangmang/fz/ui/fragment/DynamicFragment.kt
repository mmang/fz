package com.mangmang.fz.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.HasPFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.DynamicAdapter
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.PageBean
import com.mangmang.fz.ui.act.TopicDetailActivity
import com.mangmang.fz.ui.act.UserDetailActivity
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.contract.DynamicContract
import com.mangmang.fz.ui.presenter.DynamicP
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import kotlinx.android.synthetic.main.fragment_dynamic.*

/**
 * Created by mangmang on 2017/9/19.
 * 个人资料里的记录和首页公用
 */
class DynamicFragment : HasPFragment<DynamicP>(), DynamicContract.V, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {


    private var pageIndex = 1
    private val dynamicAdapter: DynamicAdapter by lazy {
        DynamicAdapter(R.layout.item_dynamic, mutableListOf())
    }
    private var uid: String = ""

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        /**
         * 判断是否是个人空间的里展示的记录 如果是 屏蔽掉点击事件
         */
        if (!TextUtils.isEmpty(uid)) return

        val data = adapter.getData()!!.get(position) as DynamicItem
        // 区分不同的页面
        when (data.idtype) {
            Constants.ALBUM_TYPE_ID -> Router.newIntent(activity)
                    .to(PhotoListActivity::class.java)
                    .putString(Constants.ALBUM_ID, data.id)
                    .putString(Constants.ID_TYPE, Constants.ALBUM_TYPE_ID)
                    .launch()
            Constants.PIC_TYPE_ID -> {
                Router.newIntent(activity)
                        .to(TopicDetailActivity::class.java)
                        .putString(Constants.TOPIC_ID, data.id)
                        .putString(Constants.ID_TYPE, Constants.PIC_TYPE_ID)
                        .launch()
            }
            Constants.OTHER_TYPE_ID -> {
            }
            else ->
                Router.newIntent(activity)
                        .to(TopicDetailActivity::class.java)
                        .putString(Constants.ID_TYPE, data.idtype)
                        .putString(Constants.TOPIC_ID, data.id)
                        .launch()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.getData()?.get(position) as DynamicItem
        when (view.id) {
            R.id.avatar -> Router
                    .newIntent(activity)
                    .to(UserDetailActivity::class.java)
                    .putString(Constants.USER_ID, data.uid)
                    .launch()
        }
    }


    override fun setData(dynamic: PageBean<DynamicItem>) {
        refreshLayout.finishLoadmore()
        refreshLayout.finishRefresh()
        refreshLayout.isEnableLoadmore = dynamic.more != 0
        dynamicAdapter.apply {
            if (pageIndex == 1) replaceData(dynamic.list)
            else addData(dynamic.list)
        }
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dynamicAdapter
        dynamicAdapter.setOnItemClickListener(this)
        dynamicAdapter.setOnItemChildClickListener(this)

        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            presenter.loadDynamicData(pageIndex, uid)
        }
        refreshLayout.setOnLoadmoreListener {
            presenter.loadDynamicData(++pageIndex, uid)
        }
    }

    override fun initData() {
        uid = arguments?.getString(Constants.USER_ID) ?: ""
        presenter.loadDynamicData(pageIndex, uid)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_dynamic
    }


    override fun showError(msg: String?) {
        super.showError(msg)
        refreshLayout.finishLoadmore()
        refreshLayout.finishRefresh()
    }
}