package com.mangmang.fz.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.TopicCommentItem
import com.mangmang.fz.bean.TopicCommentOrg
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class TopicAdapter(data: MutableList<TopicCommentItem>) : BaseMultiItemQuickAdapter<TopicCommentItem, BaseViewHolder>(data) {


    init {
        addItemType(Constants.TOPIC_ITEM_TYPE_IMG, R.layout.item_common_img)
        addItemType(Constants.TOPIC_ITEM_TYPE_TEXT, R.layout.item_common_text)
        addItemType(Constants.TOPIC_ITEM_TYPE_REPLY, R.layout.item_comment)
    }

    override fun convert(helper: BaseViewHolder, item: TopicCommentItem) {
        when (helper.itemViewType) {
            Constants.TOPIC_ITEM_TYPE_IMG -> {
                var ivItem = helper.getView<ImageView>(R.id.ivItem)
                GlideUtil.load(mContext, item.topicContent, "")
                        .into(ivItem, false, ImageView.ScaleType.FIT_CENTER)
            }
            Constants.TOPIC_ITEM_TYPE_TEXT -> {
                helper.setText(R.id.tvItem, item.topicContent)
            }
            Constants.TOPIC_ITEM_TYPE_REPLY -> {
                helper.setText(R.id.tvName, item.username)
                helper.setText(R.id.tvComment, item.topicContent)
                helper.setText(R.id.tvTime, item.time)
                var avatar = helper.getView<ImageView>(R.id.ivHead)
                GlideUtil.loadRoundImage(mContext, avatar, item.avatar)
            }
        }
    }
}