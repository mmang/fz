package com.mangmang.fz.ui.presenter

import android.text.TextUtils
import android.util.Log
import com.happyfi.lelerong.base.BasePresenter
import com.mangmang.fz.bean.UserDetailField
import com.mangmang.fz.ui.act.UserDetailActivity
import com.mangmang.fz.ui.contract.UserProfileContract
import com.mangmang.fz.utils.StringUtlis
import com.mangmang.fz.utils.applySchedulers
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class UserProfileP : BasePresenter<UserProfileContract.V>, UserProfileContract.P {

    private val fileds = mutableListOf<UserDetailField>()

    override fun loadUserProfile(uid: String) {
        apiService.getUserProfile(uid)
                .applySchedulers()
                .subscribe({
                    //因为返回的解析数据太变态，这里自己手动转换下
                    forObjectFields(it.data.data)
                    var activity = view?.getContext() as? UserDetailActivity
                    activity?.setUserProfile(it)
                    view?.setUserProfile(it, fileds)

                }, {
                    view?.showError(it.message.toString())
                })
    }

    @Inject
    constructor() : super()


    private fun forObjectFields(obj: Any) {
        var fields = obj::class.java.declaredFields
        for (field in fields) {
            Log.d("field", field.genericType.toString().equals("class com.mangmang.fz.bean.UserDetailField").toString() + "...." + field.name)
            if (field.genericType.toString().equals("class com.mangmang.fz.bean.UserDetailField")) {
                val methodName = "get${StringUtlis.toUpperCase4Index(field.name, 0)}"
                val m = obj::class.java.getMethod(methodName)
                val f = m.invoke(obj)
                if (f != null) {
//                    && !TextUtils.equals((f as UserDetailField).value, "仅自己可见")
                    this.fileds.add(f as UserDetailField)
                }
            }
        }
    }

}