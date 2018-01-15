package com.mangmang.fz.bean

/**
 * Created by wangzhenguang on 2017/12/8.
{
"photo_status": 1,
"data": {
"picid": "1693449",
"albumid": "175006",
"topicid": "0",
"uid": "517",
"username": "laoyao",
"dateline": "1512977811",
"postip": "106.120.206.155",
"filename": "\u5fae\u4fe1\u56fe\u7247_20171211152425_\u526f\u672c.jpg",
"title": "\u8bc8\u5c38",
"type": "image\/jpeg",
"size": "105807",
"filepath": "http:\/\/www.feizan.cn\/attachment\/201712\/11\/517_1512977811uf0t.jpg",
"thumb": "1",
"remote": "0",
"hot": "6",
"click_6": "0",
"click_7": "0",
"click_9": "0",
"magicframe": "0",
"click_16": "0",
"click_18": "3",
"click_21": "0",
"click_32": "0",
"thumbpic": "http:\/\/www.feizan.cn\/attachment\/201712\/11\/517_1512977811uf0t.jpg.thumb.jpg",
"comments": {
"count": "3",
"more": 0,
"page": 1,
"pageSize": 20,
"list": [{
"cid": "6016685",
"uid": "517",
"id": "1693449",
"idtype": "picid",
"authorid": "488279",
"author": "lamda",
"ip": "113.15.140.51",
"dateline": "17\u5206\u949f\u524d",
"message": "\u6cd5\u4ee4\u7eb9\u2026\u2026\u91cd\u4e86\u3002",
"magicflicker": "0",
"avatar": "http:\/\/center.feizan.cn\/avatar\/000\/48\/82\/79_avatar_small.jpg"
}]
}
}
}
 */
data class UserPhotoDetail(
        var picid: String,
        var topicid: String,
        var uid: String,
        val username: String,
        val dateline: String,
        val title: String,
        val filepath: String,
        val thumbpic: String,
        val comments: Comment?
)


data class Comment(
        val count: Int,
        val more: Int,
        val page: Int,
        val pageSize: Int,
        val list: List<PhotoComment>
)

data class PhotoComment(
        val cid: String,
        val uid: String,
        val id: String,
        val idtype: String,
        val authorid: String,
        val author: String,
        val dateline: String,
        val message: String,
        val avatar: String
)