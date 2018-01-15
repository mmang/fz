package com.mangmang.fz.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.mangmang.fz.utils.Constants

/**
 * Created by wangzhenguang on 2017/12/8.
装换topicCommentOrg
 */
class TopicDetail{
    //话题详情
    lateinit var topicId: String // 整个话题的id
    lateinit var uid: String
    lateinit var username: String
    lateinit var time: String
    lateinit var topicContent: String
    lateinit var avatar: String
    var contentImg: String = ""
    var thumbpic: String = ""
    lateinit var list: MutableList<TopicCommentItem>
    var idType = "" //日志时要设置
}


class TopicCommentItem : MultiItemEntity {
    var commentId: String = ""
    var uid: String = ""
    var topicContent: String = ""
    var time: String = ""
    var username: String = ""
    var idtype: String = ""
    var avatar: String = ""
    var topicItemType: Int = Constants.TOPIC_ITEM_TYPE_REPLY

    override fun getItemType(): Int {
        return topicItemType
    }
}

