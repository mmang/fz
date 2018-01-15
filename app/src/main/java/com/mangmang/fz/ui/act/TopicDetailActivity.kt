package com.mangmang.fz.ui.act

import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseHasPActivity
import com.mangmang.fz.LogUtils
import com.mangmang.fz.R
import com.mangmang.fz.adapter.TopicAdapter
import com.mangmang.fz.base.AndroidBug5497Workaround
import com.mangmang.fz.bean.TopicCommentItem
import com.mangmang.fz.bean.TopicDetail
import com.mangmang.fz.ui.contract.TopicDetailContract
import com.mangmang.fz.ui.presenter.TopicDetailP
import com.mangmang.fz.utils.*
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.item_topic_header_layout.view.*
import kotlinx.android.synthetic.main.layout_refresh.*
import kotlinx.android.synthetic.main.layout_reply_input.*

/**
 * Created by wangzhenguang on 2017/12/8.
 *
 *
 *
 */
class TopicDetailActivity : BaseHasPActivity<TopicDetailP>(), TopicDetailContract.V, BaseQuickAdapter.OnItemClickListener {


    private var page = 1

    private lateinit var topicid: String
    private var replyId = ""
    private lateinit var idtype: String

    private val headView by lazy { View.inflate(this, R.layout.item_topic_header_layout, null) }
    val adapter by lazy { TopicAdapter(mutableListOf()) }

    override fun getLayoutId(): Int {
        return R.layout.activity_topic_detail
    }


    override fun initView() {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content))
        tvTitle.text = "评论列表"
        adapter.setHeaderView(headView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration
                .Builder(this)
                .color(resources.getColor(R.color.lineColor)).build())

        adapter.onItemClickListener = this
    }

    override fun initlistener() {
        idtype = intent.getStringExtra(Constants.ID_TYPE)
        refreshLayout.setOnLoadmoreListener {
            presenter.getTopicData(topicid, ++page, idtype)
        }
        refreshLayout.setOnRefreshListener {
            page = 1
            presenter.getTopicData(topicid, page, idtype)
        }

        btnReply.setOnClickListener {
            //回复
            presenter.reply(topicid, replyId, idtype, etInput.text.toString())
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val itemViewType = adapter.getItemViewType(position + adapter.headerLayoutCount)
        when (itemViewType) {
            Constants.TOPIC_ITEM_TYPE_REPLY -> {
                //TopicCommentItem
                //取出回复评论的id ， 名字
                val item = adapter.data[position] as TopicCommentItem
                replyId = item.commentId
                showSoftInputFromWindow(etInput)
                etInput.hint = "@ ${item.username}"
            }
        }
    }

    override fun initDatas() {
        topicid = intent.getStringExtra(Constants.TOPIC_ID)
        presenter.loadData()
    }

    override fun autoRefresh() {
        refreshLayout.autoRefresh()
    }

    override fun setTopicData(t: TopicDetail) {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadmore()
        if (page == 1) {
            setHeaderView(t)
            adapter.setNewData(t.list)
        } else {
            adapter.addData(t.list)
        }
    }

    /**
     * 设置图片评论数据
     * 图片第一页的数据 请求图片的时候就有
     * 后面的评论又是一个接口。。。。。。。。。。。。。
     */
    override fun setPhotoReply(t: MutableList<TopicCommentItem>) {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadmore()
        adapter.addData(t)
    }

    override fun showError(msg: String?) {
        super.showError(msg)
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadmore()
    }

    override fun enableLoadMore(b: Boolean) {
        refreshLayout.isEnableLoadmore = b
    }


    override fun replySuccess() {
        //回复成功 清空数据
        etInput.setText("")
        hideSoftInputFromWindow()
        replyId = ""
        showToast("回复成功")
    }

    override fun replyError() {
        hideSoftInputFromWindow()
        replyId = ""
        showToast("回复失败，请重试！")
    }

    private fun setHeaderView(data: TopicDetail) {
        GlideUtil.loadRoundImage(this, headView.ivHead, data.avatar)
        headView.tvName.text = data.username
        //需要判断时间是毫秒值还是已经算好的天数。
        headView.tvTime.text = data.time
        //判断有没有文字
        if (data.topicContent == null || data.topicContent.isEmpty() || data.idType == Constants.BLOG_TYPE_ID) {
            headView.tvTitle.visibility = View.GONE
        }
        if (data.idType == Constants.BLOG_TYPE_ID) {
            headView.webView.settings.defaultTextEncodingName = "UTF-8"
            headView.webView.loadData(data.topicContent, "text/html;charset=UTF-8", null)
        } else {
            headView.tvTitle.text = data.topicContent
        }

        //判断有没有图片
        if (!data.contentImg.isEmpty()) {
            headView.ivPic.visibility = View.VISIBLE
            Glide.with(this)
                    .load(data.thumbpic)
                    .into(object : SimpleTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            //根据图片计算一下 view的宽高
                            val intrinsicWidth = resource.intrinsicWidth
                            val intrinsicHeight = resource.intrinsicHeight
                            LogUtils.d("宽度： $intrinsicWidth   高度  ：$intrinsicHeight")

                            val dm = DisplayMetrics()
                            windowManager.defaultDisplay.getMetrics(dm)
                            val width = dm.widthPixels
                            val imageHeight = width * intrinsicHeight / intrinsicWidth
                            val layoutParams = headView.ivPic.layoutParams
                            layoutParams.height = imageHeight
                            layoutParams.width = width
                            headView.ivPic.layoutParams = layoutParams

                            GlideUtil.load(this@TopicDetailActivity, data.contentImg, data.thumbpic)
                                    .into(headView.ivPic, false, ImageView.ScaleType.FIT_CENTER)
                        }
                    })
        } else headView.ivPic.visibility = View.GONE
        adapter.notifyDataSetChanged()

    }


}