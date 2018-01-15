package com.mangmang.fz.bean

/**
 * Created by mangmang on 2017/9/18.
 */

//data class User(
//		val auth_token: String,// 3b14b53a7555cfd35333495157b078aa
//		val account_status: Int,// 1
//		val data: Data
//)

data class User(
		val uid: String,// 2328052
		val username: String,// 茫忙
		val token: String,// 3b14b53a7555cfd35333495157b078aa
		val data: UserData,
		val dateline: String// 2017-09-18 16:15:42
)


data class UserData(
		val uid: String,// 2328052
		val sex: String,// 1
		val email: String,// 610880568@qq.com
		val newemail: String,//
		val emailcheck: String,// 0
		val mobile: String,//
		val qq: String,// 610880568
		val msn: String,//
		val msnrobot: String,//
		val msncstatus: String,// 0
		val videopic: String,//
		val birthyear: String,// 1992
		val birthmonth: String,// 4
		val birthday: String,// 13
		val blood: String,//
		val marry: String,// 1
		val birthprovince: String,// 湖南
		val birthcity: String,// 娄底
		val resideprovince: String,// 上海
		val residecity: String,// 虹口
		val note: String,//
		val spacenote: String,//
		val authstr: String,//
		val theme: String,//
		val nocss: String,// 0
		val menunum: String,// 0
		val css: String,//
		val privacy: String,//
		val sendmail: String,//
		val magicstar: String,// 0
		val magicexpire: String,// 0
		val timeoffset: String,//
		val online: Int,// 1
		val lastlogin: String,// 12分钟前
		val username: String,// 茫忙
		val friend_count: String,// 0
		val photo_count: String,// 0
		val blog_count: String,// 0
		val doing_count: String,// 0
		val fans_count: String,// 0
		val follows_count: String,// 0
		val message: String,//
		val msg_id: String,//
		val isfriend: Int,// -1
		val field_22: Field_22,
		val fields: List<String>,
		val field_25: Field_25,
		val field_31: Field_31,
		val field_12: Field_12,
		val field_13: Field_13,
		val field_5: Field_5,
		val field_32: Field_32,
		val field_33: Field_33,
		val field_20: Field_20,
		val field_21: Field_21,
		val field_30: Field_30,
		val field_34: Field_34,
		val field_1: Field_1,
		val field_14: Field_14,
		val field_15: Field_15,
		val field_24: Field_24,
		val field_35: Field_35,
		val field_36: Field_36,
		val field_16: Field_16,
		val field_18: Field_18,
		val field_27: Field_27,
		val avatar: String,// http://center.feizan.cn/avatar/002/32/80/52_avatar_middle.jpg
		val big_avatar: String,// http://center.feizan.cn/avatar/002/32/80/52_avatar_big.jpg
		val visited_count: Int,// 0
		val visit_count: Int// 0
)

data class Field_13(
		val name: String,// 喝酒
		val note: String,//
		val value: String,// 不喝酒
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 13
)

data class Field_15(
		val name: String,// 是否结婚
		val note: String,//
		val value: String,// 不结婚
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 15
)

data class Field_12(
		val name: String,// 吸烟
		val note: String,//
		val value: String,// 不吸烟
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 12
)

data class Field_21(
		val name: String,// 体重（公斤）
		val note: String,//
		val value: String,// 65
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 21
)

data class Field_5(
		val name: String,// 体型
		val note: String,//
		val value: String,// 正常
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 5
)

data class Field_16(
		val name: String,// 星座
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 16
)

data class Field_36(
		val name: String,// 恋爱经验
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 36
)

data class Field_22(
		val name: String,// 网址
		val note: String,// 官网，博客地址等
		val value: String,//
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 22
)

data class Field_33(
		val name: String,// 头发
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 33
)

data class Field_14(
		val name: String,// 出柜情况
		val note: String,//
		val value: String,// 完全未出柜
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 14
)

data class Field_30(
		val name: String,// 宗教信仰
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 30
)

data class Field_1(
		val name: String,// 恋爱倾向
		val note: String,//
		val value: String,// 同性爱
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 1
)

data class Field_18(
		val name: String,// 职业
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 18
)

data class Field_27(
		val name: String,// 情侣
		val note: String,// 如果你有情侣并且也在飞赞，请填写TA的飞赞用户名
		val value: String,//
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 27
)

data class Field_24(
		val name: String,// 恋爱角色
		val note: String,//
		val value: String,// 我不认可角色划分
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 24
)

data class Field_20(
		val name: String,// 身高（厘米）
		val note: String,//
		val value: String,// 175
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 20
)

data class Field_25(
		val name: String,// 微博
		val note: String,// 请关注飞赞微博：weibo.com/feizan
		val value: String,//
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 25
)

data class Field_32(
		val name: String,// 体毛
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 32
)

data class Field_34(
		val name: String,// 教育程度
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 34
)

data class Field_35(
		val name: String,// 异地恋
		val note: String,//
		val value: String,//
		val choice: List<String>,
		val formtype: String,// select
		val fieldid: String// 35
)

data class Field_31(
		val name: String,// 微信
		val note: String,// 飞赞官方微信号：ifeizan
		val value: String,//
		val choice: List<String>,
		val formtype: String,// text
		val fieldid: String// 31
)