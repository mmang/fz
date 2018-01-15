package com.mangmang.fz.utils

/**
 * Created by mangmang on 2017/9/25.
 */
object Constants {

    const val REQUEST_SUCCESS = 1
    const val REQUEST_FAIL = -1
    const val NULL_ERROR ="null_error"

    /**
     * intent 传入字段
     */
    const val USER_ID = "user_Id"
    /**
     * intent 传入字段
     */
    const val TOPIC_ID = "topic_id"

    /**
     * 获取所有照面的type
     *
     */
    val PHOTO_TYPE_HOT = "hot"
    val PHOTO_TYPE_NEW = "new"

    /**
     * 动态 话题条目的类型
     */
    const val ID_TYPE = "id_type" //用来判断话题的类型

    const val BLOG_TYPE_ID = "blogid" //评论日志
    const val ALBUM_TYPE_ID = "albumid" //更新相册
    const val DO_TYPE_ID = "doid"// 发表记录
    const val PIC_TYPE_ID = "picid" //评论图片
    const val OTHER_TYPE_ID = "other" //评论图片

    const val TOPIC_ITEM_TYPE_TEXT = 1
    const val TOPIC_ITEM_TYPE_IMG = 2
    const val TOPIC_ITEM_TYPE_REPLY = 3

    /**
     * 相册 - > 照片列表 所需参数
     */
    val ALBUM_ID: String = "album_id"
    /**
     * 照片url
     */
    val PHOTO_URL: String = "PHOTO_URL"
    /**
     * 缩略图地址
     */
    val PHOTO_THUMBNAIL_URL: String = "PHOTO_THUMBNAIL_URL"
    /**
     * 进入图片预览 参数key
     */
    val ALL_PHOTO: String ="all photo"
    val INDEX: String = "index" //进入预览时 第一次显示图片的位置
}