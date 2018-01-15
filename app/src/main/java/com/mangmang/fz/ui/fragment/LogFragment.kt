package com.mangmang.fz.ui.fragment

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseFragment
import com.happyfi.lelerong.base.BaseInjectorFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.DynamicAdapter
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.PageBean
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.ui.act.TopicDetailActivity
import com.mangmang.fz.ui.act.UserDetailActivity
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.trello.rxlifecycle2.android.FragmentEvent
import kotlinx.android.synthetic.main.layout_refresh.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 * 个人资料里的 日志和首页日志公用
 *
 */
class LogFragment : BaseInjectorFragment(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    var pageIndex = 1
    lateinit var dynamicAdapter: DynamicAdapter

    @Inject
    lateinit var apiservice: ApiService

    override fun getLayoutResources(): Int {
        return R.layout.layout_refresh
    }


    override fun initView() {
        this.rootView?.background = ColorDrawable(resources.getColor(R.color.lineColor))
        recyclerView.layoutManager = LinearLayoutManager(context)
        dynamicAdapter = DynamicAdapter(R.layout.item_dynamic, mutableListOf())
        recyclerView.adapter = dynamicAdapter
        dynamicAdapter.onItemClickListener = this
        dynamicAdapter.onItemChildClickListener = this
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            initData()
        }
        refreshLayout.setOnLoadmoreListener {
            pageIndex++
            initData()
        }

    }

    override fun initData() {
        val uid = arguments?.getString(Constants.USER_ID)
        //有用户id就获取用户id
        if (TextUtils.isEmpty(uid))
            getHotBlogs()
        else
            getBlogListByUser(uid!!)
    }


    /**
     * 获取的当前用户的日志。返回的数据中没有头像 需要从其他页面传入过来
     */
    private fun getBlogListByUser(uid: String) {
        apiservice.getBlogListByUser(uid, pageIndex)
                .compose(this.bindUntilEvent(FragmentEvent.DETACH))
                .applySchedulers()
                .subscribe({
                    if (it.status == Constants.REQUEST_SUCCESS) {
                        it.data?.list?.forEach {
                            it.avatar = ""
                            it.idtype = Constants.BLOG_TYPE_ID
                        }
                        setData(it.data)
                    } else showToast("加载失败")
                }, {
                    showToast(it.message)
                })
    }

    private fun getHotBlogs() {
        apiservice.getHotBlogs(30, pageIndex)
                .compose(this.bindUntilEvent(FragmentEvent.DETACH))
                .applySchedulers()
                .subscribe({
                    if (it.status == Constants.REQUEST_SUCCESS)
                        setData(it.data)
                    else showToast("加载失败")
                }, {
                    showToast(it.message)
                })
    }

    private fun setData(dynamic: PageBean<DynamicItem>?) {
        refreshLayout.finishLoadmore()
        refreshLayout.finishRefresh()
        refreshLayout.isEnableLoadmore = dynamic?.more != 0
        if (dynamic != null && dynamic.list != null) {
            dynamicAdapter.apply {
                if (pageIndex == 1) replaceData(dynamic.list)
                else addData(dynamic.list)
            }
        }
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.getData()!!.get(position) as DynamicItem
        // 区分不同的页面
        when (data.idtype) {
            Constants.ALBUM_TYPE_ID -> Router.newIntent(activity)
                    .to(PhotoListActivity::class.java)
                    .putString(Constants.ALBUM_ID, data.id)
                    .launch()
            Constants.PIC_TYPE_ID -> {
                Router.newIntent(activity)
                        .to(TopicDetailActivity::class.java)
                        .putString(Constants.TOPIC_ID, data.id)
                        .putString(Constants.ID_TYPE, data.idtype)
                        .launch()
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

}