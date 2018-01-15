package com.mangmang.fz.net

import android.content.Intent
import com.mangmang.fz.DialogManager
import com.mangmang.fz.FZApplication
import com.mangmang.fz.LogUtils
import com.mangmang.fz.base.BaseResponse
import com.mangmang.fz.net.exception.ExceptionHandle
import com.mangmang.fz.ui.act.LoginAtivity
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.showToast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by wangzhenguang on 2018/1/4.
 */
abstract class BaseObserver<T> : Observer<BaseResponse<T>> {


    override fun onComplete() {
        DialogManager.dissLoadDialog()
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onNext(value: BaseResponse<T>?) {
        /**
         * 处理业务逻辑
         * 全局的状态控制入口
         */
        value?.let {
            value.status = if (value.error == Constants.NULL_ERROR) Constants.REQUEST_FAIL else value.status
        }
        when (value?.status) {
            Constants.REQUEST_FAIL -> {
                //全局检查token过期
                if ("token invaild".equals(value?.message) && value?.status == -1) {
                    /**
                     * token过期  跳转到
                     */
                    val intent = Intent(FZApplication.instance, LoginAtivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    FZApplication.instance.startActivity(intent)
                }
                onFail(value.status, value.message)
            }
            else -> {
                if (value == null) {
                    onFail(10000, "未获取到数据")
                    return
                }
                onSuccess(value)
            }
        }

    }

    override fun onError(e: Throwable) {
        val e = ExceptionHandle().handleException(e)
        _onError(e)
    }

    private fun _onError(e: ExceptionHandle.ResponeThrowable) {
        onFail(e.code, e.message ?: "未知错误 code ${e.code}")
    }


    /**
     * 失败的回调
     */
    open fun onFail(status: Int, message: String) {
        FZApplication.instance.showToast(message)
        LogUtils.d("$status : $message")
    }

    abstract fun onSuccess(value: BaseResponse<T>)


}

fun <T> Observable<BaseResponse<T>>.requestCallBack(onSuccess: (data: BaseResponse<T>) -> Unit, onFail: (code: Int, msg: String) -> Unit) {
    return this.subscribe(object : BaseObserver<T>() {
        override fun onSuccess(value: BaseResponse<T>) {
            onSuccess(value)
        }

        override fun onFail(status: Int, message: String) {
            onFail(status, message)
        }

    })
}

fun <T> Observable<BaseResponse<T>>.requestCallBack(onSuccess: (data: BaseResponse<T>) -> Unit) {
    return this.subscribe(object : BaseObserver<T>() {
        override fun onSuccess(value: BaseResponse<T>) {
            onSuccess(value)
        }
    })
}