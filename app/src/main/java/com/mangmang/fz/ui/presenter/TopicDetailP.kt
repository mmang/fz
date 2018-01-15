package com.mangmang.fz.ui.presenter

import com.happyfi.lelerong.base.BasePresenter
import com.mangmang.fz.LogUtils
import com.mangmang.fz.base.BaseResponse
import com.mangmang.fz.bean.*
import com.mangmang.fz.ui.contract.TopicDetailContract
import com.mangmang.fz.utils.Constants
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * Created by wangzhenguang on 2017/12/9.
 */
class TopicDetailP : BasePresenter<TopicDetailContract.V>, TopicDetailContract.P {


    @Inject
    constructor() : super()

    private var page = 1

    override fun getTopicData(id: String, page: Int, idtype: String) {
        this.page = page
        when (idtype) {
            Constants.PIC_TYPE_ID -> { //判断下page 如果page>1 访问另外的接口拿评论数据
                requestPicReply(page, id) //图片评论
            }
            Constants.BLOG_TYPE_ID -> {
                //日志 返回的是html的格式  需要自己解析
                //blog的评论也需要另外拉
                val blogReply = apiService.getBlogReply(id, page)
                if (page == 1) {
                    val blog = apiService.getBlog(id)
                    Observable.zip(blog, blogReply, BiFunction<BaseResponse<Blog>,
                            BaseResponse<Comment>, TopicDetail> { blog, blogReply ->
                        //解析 返回的内容。 如果blog没有的话就显示失败
                        //解析日志
                        var topicDetail: TopicDetail? = null
                        if (blog.status == Constants.REQUEST_SUCCESS && blog.status == Constants.REQUEST_SUCCESS) {
                            val data = blog.data
                            data?.let {
                                topicDetail = converTopicDetail(data)
                                view?.enableLoadMore(blogReply.data!!.more != 0) //如果没有更过的平路就禁止加载更多
                                val comments = converTopicDetail(blogReply)
                                topicDetail?.list?.addAll(comments)
                            }
                        }
                        topicDetail //返回数据
                    }).compose(applySchedulers())
                            .subscribe({
                                if (it == null) {
                                    view?.showError("获取失败，请重试！")
                                } else {
                                    view?.setTopicData(it)
                                }
                            }, {
                                view?.showError(it.message!!)
                            })
                } else {
                    //单独啦
                    blogReply.compose(applySchedulers())
                            .subscribe({ response ->
                                if (response.status == Constants.REQUEST_SUCCESS) {
                                    val data = response.data
                                    data?.let {
                                        view?.enableLoadMore(it.more != 0) //如果没有更过的平路就禁止加载更多
                                        val comments = converTopicDetail(response)
                                        view?.setPhotoReply(comments)
                                    }
                                } else {
                                    view?.showError("获取失败，请重试！")
                                }
                            }, {
                                view?.showError(it.message!!)
                            })
                }
            }
            else -> {
                apiService.getStatusComment(id, page)
                        .compose(applySchedulers())
                        .subscribe({ t: BaseResponse<TopicCommentOrg> ->
                            if (t.status == Constants.REQUEST_SUCCESS) {
                                //由于接口那边的数据格式不统一，而我这边又想通用。 所以建一个新的bean 手动转化下
                                val topicDetail = converTopicDetail(t)
                                view?.setTopicData(topicDetail)
                            } else {
                                view?.showError("获取失败，请重试！")
                            }
                        }, { e: Throwable ->
                            view?.showError(e.message!!)
                        })
            }
        }
    }

    override fun reply(topicId: String, replyId: String, idtype: String, replyText: String) {
        val params = appendParams(idtype, replyText, replyId, topicId)
        view?.showLoading()
        apiService.reply(params)
                .compose(applySchedulers())
                .subscribe({
                    view?.dismissLoading()
                    view?.replySuccess()
                }, {
                    view?.replyError()
                })
    }

    private fun appendParams(idtype: String, replyText: String, replyId: String, topicId: String): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.apply {
            var m = ""
            var a = ""
            var reply = ""
            var replyid = ""
            when (idtype) {
                Constants.BLOG_TYPE_ID -> {
                    m = "user/blog"
                    a = "addBlogReply"
                    reply = "comment"
                    replyid = "blogId"
                }
                Constants.PIC_TYPE_ID -> {
                    m = "user/photo"
                    a = "addPhotoReply"
                    reply = "comment"
                    replyid = "id"

                }
                Constants.DO_TYPE_ID -> {
                    m = "user/user"
                    a = "replyStatus"
                    reply = "message"
                    replyid = "messageId"
                }
            }
            put("m", m)
            put("a", a)
            put(reply, replyText)
            put(replyid, topicId)



            //评论日志评论
            if (replyId.isNotEmpty()) {
                put("cid", replyId)
            }

        }
        return params
    }


    /**
     * 图片 评论相关逻辑
     */
    private fun requestPicReply(page: Int, id: String) {
        when (page) {
            1 -> {
                apiService.getPhotoMessage(id)
                        .compose(applySchedulers())
                        .subscribe({
                            if (it.status == Constants.REQUEST_SUCCESS) {
                                val topicDetail = converTopicDetail(it.data)
                                view?.setTopicData(topicDetail)
                                view?.enableLoadMore(it.data!!.comments!!.more != 0)

                            } else {
                                view?.showError("获取失败，请重试！")
                            }
                        }, {
                            view?.showError(it.message)
                        })
            }
            else -> {
                //调用获取图片评论数据
                apiService.getPhotoReply(id, page.toString())
                        .compose(applySchedulers())
                        .subscribe({
                            if (it.status == Constants.REQUEST_SUCCESS) {
                                view?.enableLoadMore(it.data!!.more != 0)
                                if (it.data?.list != null && it.data!!.list.isNotEmpty()) {
                                    val list = mutableListOf<TopicCommentItem>()
                                    it.data!!.list.forEach { data ->
                                        val item = TopicCommentItem()
                                        item.apply {
                                            commentId = data.id
                                            uid = data.authorid
                                            topicContent = data.message
                                            time = data.dateline
                                            idtype = data.idtype
                                            username = data.author
                                            avatar = data.avatar
                                            topicItemType = Constants.TOPIC_ITEM_TYPE_REPLY

                                        }
                                        list.add(item)
                                    }
                                    view?.setPhotoReply(list)
                                }

                            } else {
                                view?.showError("获取失败，请重试！")
                            }
                        }, {
                            22
                            view?.showError(it.message)
                        })

            }
        }
    }


    override fun loadData() {
        view?.autoRefresh()
    }

    /**
     * 转化日志单独拉取时的评论数据
     */
    private fun converTopicDetail(blogReply: BaseResponse<Comment>): MutableList<TopicCommentItem> {
        val list = mutableListOf<TopicCommentItem>()
        blogReply.data!!.list?.forEach { data ->
            val item = TopicCommentItem()
            item.apply {
                commentId = data.id
                uid = data.authorid
                topicContent = data.message
                time = data.dateline
                idtype = data.idtype
                username = data.author
                avatar = data.avatar
                topicItemType = Constants.TOPIC_ITEM_TYPE_REPLY
            }
            list.add(item)
        }
        return list
    }

    /**
     * 转化 普通的记录数据评论
     */
    private fun converTopicDetail(t: BaseResponse<TopicCommentOrg>): TopicDetail {
        val topicDetail = TopicDetail()
        val data = t.data
        if (data != null) {
            topicDetail.topicId = data.doid
            topicDetail.avatar = data.avatar
            topicDetail.uid = data.uid
            topicDetail.username = data.username
            topicDetail.time = data.dateline
            topicDetail.topicContent = data.message
            topicDetail.list = mutableListOf<TopicCommentItem>()
            if (data.list != null) {
                data.list.forEach { data ->

                    val item = TopicCommentItem()
                    item.apply {
                        commentId = data.id
                        uid = data.uid
                        topicContent = data.message
                        time = data.dateline
                        username = data.username
                        avatar = data.avatar
                        topicItemType = Constants.TOPIC_ITEM_TYPE_REPLY
                    }
                    topicDetail.list.add(item)
                }
            }
        }
        return topicDetail
    }

    /**
     * 转化图片评论数据
     */
    private fun converTopicDetail(data: UserPhotoDetail?): TopicDetail {
        val topicDetail = TopicDetail()
        if (data != null) {
            topicDetail.topicId = data.picid
            topicDetail.avatar = ""
            topicDetail.uid = data.uid
            topicDetail.username = data.username
            topicDetail.time = data.dateline
            topicDetail.topicContent = data.title
            topicDetail.list = mutableListOf()
            topicDetail.contentImg = data.filepath
            topicDetail.thumbpic = data.thumbpic

            data.comments?.list?.let {
                data.comments.list.forEach {
                    val item = TopicCommentItem().apply {
                        commentId = it.id
                        uid = it.authorid
                        topicContent = it.message
                        time = it.dateline
                        username = it.author
                        idtype = it.idtype
                        avatar = it.avatar
                        topicItemType = Constants.TOPIC_ITEM_TYPE_REPLY
                    }
                    topicDetail.list.add(item)
                }
            }
        }
        return topicDetail
    }

    //转化日志数据
    private fun converTopicDetail(blog: Blog): TopicDetail {
        val topicDetail = TopicDetail()
        topicDetail.topicId = blog.blogid
        topicDetail.avatar = blog.avatar
        topicDetail.uid = blog.uid
        topicDetail.username = blog.username
        topicDetail.time = blog.dateline
        topicDetail.topicContent = blog.subject
        topicDetail.list = mutableListOf()
        topicDetail.contentImg = ""
        topicDetail.thumbpic = ""
        topicDetail.idType = Constants.BLOG_TYPE_ID
        //解析出 message 里的html
        val document = Jsoup.parse(blog.message)
        val container = document.getElementById("container")
        LogUtils.d(container.text())
        topicDetail.topicContent = container.html()
        return topicDetail
    }

}