package com.mangmang.fz.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by mangmang on 2017/9/25.
 */


data class UserDetail(
		@SerializedName("user_status") val userStatus: Int, //1
		@SerializedName("data") val data: UserDetailData
)

data class UserDetailData(
		@SerializedName("data") val data: UserDetailInnerData
)

data class UserDetailInnerData(
		@SerializedName("uid") val uid: String, //417179
		@SerializedName("sex") val sex: String, //1
		@SerializedName("newemail") val newemail: String, //1875898202@qq.com
		@SerializedName("emailcheck") val emailcheck: String, //1
		@SerializedName("mobile") val mobile: String,
		@SerializedName("qq") val qq: String, //对好友可见
		@SerializedName("msn") val msn: String, //对好友可见
		@SerializedName("msnrobot") val msnrobot: String,
		@SerializedName("msncstatus") val msncstatus: String, //0
		@SerializedName("videopic") val videopic: String,
		@SerializedName("birthyear") val birthyear: String, //1991
		@SerializedName("birthmonth") val birthmonth: String, //4
		@SerializedName("birthday") val birthday: String, //23
		@SerializedName("blood") val blood: String,
		@SerializedName("marry") val marry: String, //1
		@SerializedName("birthprovince") val birthprovince: String, //湖北
		@SerializedName("birthcity") val birthcity: String,
		@SerializedName("resideprovince") val resideprovince: String, //湖北
		@SerializedName("residecity") val residecity: String, //武汉
		@SerializedName("note") val note: String, //为什么我的Aloha一直在维护中？维护了几天了
		@SerializedName("spacenote") val spacenote: String, //为什么我的Aloha一直在维护中？维护了几天了
		@SerializedName("authstr") val authstr: String,
		@SerializedName("theme") val theme: String,
		@SerializedName("nocss") val nocss: String, //0
		@SerializedName("menunum") val menunum: String, //0
		@SerializedName("css") val css: String,
		@SerializedName("privacy") val privacy: String,
		@SerializedName("sendmail") val sendmail: String,
		@SerializedName("magicstar") val magicstar: String, //0
		@SerializedName("magicexpire") val magicexpire: String, //0
		@SerializedName("timeoffset") val timeoffset: String,
		@SerializedName("online") val online: Int, //1
		@SerializedName("lastlogin") val lastlogin: String, //8分钟前
		@SerializedName("username") val username: String, //sogayoga
		@SerializedName("friend_count") val friendCount: String, //61
		@SerializedName("photo_count") val photoCount: String, //1
		@SerializedName("blog_count") val blogCount: String, //0
		@SerializedName("doing_count") val doingCount: String, //248
		@SerializedName("fans_count") val fansCount: String, //122
		@SerializedName("follows_count") val followsCount: String, //61
		@SerializedName("message") val message: String, //为什么我的Aloha一直在维护中？维护了几天了
		@SerializedName("msg_id") val msgId: String, //2560084
		@SerializedName("isfriend") val isfriend: Int, //0
		@SerializedName("isfans") val isfans: String, //0
        @SerializedName("avatar") val avatar: String, //http://center.feizan.cn/avatar/000/41/71/79_avatar_middle.jpg
        @SerializedName("big_avatar") val bigAvatar: String, //http://center.feizan.cn/avatar/000/41/71/79_avatar_big.jpg
        @SerializedName("visited_count") val visitedCount: Int, //0
        @SerializedName("visit_count") val visitCount: Int, //0
        @SerializedName("is_self") val isSelf: Int ,//

		@SerializedName("info_trainwith") val infoTrainwith: UserDetailField,
		@SerializedName("info_interest") val infoInterest: UserDetailField,
		@SerializedName("info_book") val infoBook: UserDetailField,
		@SerializedName("info_movie") val infoMovie: UserDetailField,
		@SerializedName("info_tv") val infoTv: UserDetailField,
		@SerializedName("info_music") val infoMusic: UserDetailField,
		@SerializedName("info_game") val infoGame: UserDetailField,
		@SerializedName("info_sport") val infoSport: UserDetailField,
		@SerializedName("info_idol") val infoIdol: UserDetailField,
		@SerializedName("info_motto") val infoMotto: UserDetailField,
		@SerializedName("info_wish") val infoWish: UserDetailField,
		@SerializedName("info_intro") val infoIntro: UserDetailField,

		@SerializedName("fields") val fields: List<String>,
        @SerializedName("field_1") val field1: UserDetailField,
        @SerializedName("field_2") val field2: UserDetailField,
        @SerializedName("field_3") val field3: UserDetailField,
        @SerializedName("field_4") val field4: UserDetailField,
        @SerializedName("field_5") val field5: UserDetailField,
        @SerializedName("field_6") val field6: UserDetailField,
        @SerializedName("field_7") val field7: UserDetailField,
        @SerializedName("field_8") val field8: UserDetailField,
        @SerializedName("field_9") val field9: UserDetailField,
        @SerializedName("field_10") val field10: UserDetailField,
        @SerializedName("field_11") val field11: UserDetailField,
        @SerializedName("field_12") val field12: UserDetailField,
        @SerializedName("field_13") val field13: UserDetailField,
        @SerializedName("field_14") val field14: UserDetailField,
        @SerializedName("field_15") val field15: UserDetailField,
        @SerializedName("field_16") val field16: UserDetailField,
        @SerializedName("field_17") val field17: UserDetailField,
        @SerializedName("field_18") val field18: UserDetailField,
        @SerializedName("field_19") val field19: UserDetailField,
        @SerializedName("field_20") val field20: UserDetailField,
        @SerializedName("field_21") val field21: UserDetailField,
        @SerializedName("field_22") val field22: UserDetailField,
        @SerializedName("field_23") val field23: UserDetailField,
        @SerializedName("field_24") val field24: UserDetailField,
        @SerializedName("field_25") val field25: UserDetailField,
        @SerializedName("field_26") val field26: UserDetailField,
        @SerializedName("field_27") val field27: UserDetailField,
        @SerializedName("field_28") val field28: UserDetailField,
        @SerializedName("field_29") val field29: UserDetailField,
        @SerializedName("field_30") val field30: UserDetailField,
        @SerializedName("field_31") val field31: UserDetailField,
		@SerializedName("field_32") val field32: UserDetailField,
		@SerializedName("field_33") val field33: UserDetailField,
		@SerializedName("field_34") val field34: UserDetailField,
		@SerializedName("field_35") val field35: UserDetailField,
		@SerializedName("field_36") val field36: UserDetailField
)


data class UserDetailField (
		@SerializedName("name") val name: String, //微博
		@SerializedName("note") val note: String, //请关注飞赞微博：weibo.com/feizan
		@SerializedName("value") val value: String,
		@SerializedName("choice") val choice: List<String>,
		@SerializedName("formtype") val formtype: String, //text
		@SerializedName("fieldid") val fieldid: String //25
)


