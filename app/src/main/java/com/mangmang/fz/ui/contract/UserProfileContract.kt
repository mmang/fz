package com.mangmang.fz.ui.contract

import com.happyfi.lelerong.base.BaseContract
import com.mangmang.fz.bean.UserDetail
import com.mangmang.fz.bean.UserDetailField

/**
 * Created by mangmang on 2017/8/29.
 */

interface UserProfileContract {

    interface V : BaseContract.BaseV {
        fun setUserProfile(it: UserDetail, fileds: MutableList<UserDetailField>)
    }

    interface P {
        fun loadUserProfile(uid: String)
    }

}