package com.mangmang.fz.ui.contract

import com.happyfi.lelerong.base.BaseContract
import com.mangmang.fz.bean.TopicCommentItem
import com.mangmang.fz.bean.TopicDetail

/**
 * Created by mangmang on 2017/8/29.
 */

interface TopicDetailContract {

    interface V : BaseContract.BaseV {

        fun autoRefresh()
        fun setTopicData(t: TopicDetail)
        fun setPhotoReply(t: MutableList<TopicCommentItem>)
        fun enableLoadMore(b: Boolean)
        fun replySuccess()
        fun replyError()
    }

    interface P {
        fun getTopicData(id: String, page: Int, idtype: String)
        fun reply(topicId: String, replyId: String, idtype: String, replyText: String)
    }

}