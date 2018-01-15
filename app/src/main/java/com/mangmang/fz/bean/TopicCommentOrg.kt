package com.mangmang.fz.bean


/**
 * Created by wangzhenguang on 2017/12/8.
 * 这个接口返回的数据
 *{
"status": 1,
"data": {
"doid": "2569276",
"uid": "2316742",
"username": "\u5f26\u4e4b\u52bc",
"from": "",
"dateline": "1\u5c0f\u65f6\u524d",
"message": "\u820c\u5934\u597d\u75db\uff0c\u4e5f\u6ca1\u8d77\u6ce1\uff0c\u4e0d\u77e5\u548b\u56de\u4e8b[\u8868\u60c5]",
"ip": "124.207.31.104",
"replynum": "7",
"mood": "20",
"avatar": "http:\/\/center.feizan.cn\/avatar\/002\/31\/67\/42_avatar_small.jpg",
"count": "7",
"more": 0,
"page": "1",
"pageSize": "25",
"list": [{
"id": "4211480",
"upid": "4211473",
"doid": "2569276",
"uid": "9636",
"username": "n8923",
"dateline": "18\u5206\u949f\u524d",
"message": "\u56de\u590d@\u5f26\u4e4b\u52bc:\u5bf9\u7684",
"ip": "218.62.244.142",
"grade": "3",
"avatar": "http:\/\/center.feizan.cn\/avatar\/000\/00\/96\/36_avatar_small.jpg"
}]
}
}
 */
data class TopicCommentOrg(var doid: String, // 整个话题的id
                           var uid: String,
                           var username: String,
                           var from: String,
                           var upid: String,
                           var dateline: String,
                           var message: String,
                           var avatar: String,
                           var list: MutableList<TopicCommentOrg>, //整个话题评论的列表
                           var id: String //此评论的id
)
