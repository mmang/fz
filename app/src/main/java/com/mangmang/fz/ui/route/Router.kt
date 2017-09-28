package com.mangmang.fz.ui.route

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import java.io.Serializable
import java.util.*

/**
 * Created by wanglei on 2016/11/29.
 */
class Router private constructor() {

    private var intent: Intent? = null;
    private var from: Activity? = null

    private var options: ActivityOptionsCompat? = null
    private var data: Bundle? = null
    private var to: Class<*>? = null
    private var requestCode = -1
    private var enterAnim = 0
    private var exitAnim = 0

    companion object {
        var callback: RouterCallback? = null
        val RES_NONE = -1
        fun newIntent(context: Activity): Router {
            val router = Router()
            router.from = context
            return router
        }

        fun pop(activity: Activity) {
            activity.finish()
        }

        fun setCallBack(callback: RouterCallback) {
            Router.callback = callback
        }
    }

    init {
        intent = Intent()
    }


    fun to(to: Class<*>): Router {
        this.to = to
        return this
    }


    fun addFlags(flag: Int): Router {

        intent?.addFlags(flag)

        return this
    }

    fun putBundle(bundle: Bundle): Router {
        this.data = bundle
        return this
    }


    fun putByte(key: String, value: Byte): Router {
        getBundleData().putByte(key, value)
        return this
    }


    fun putChar(key: String, value: Char): Router {
        getBundleData().putChar(key, value)
        return this
    }


    fun putInt(key: String, value: Int): Router {
        getBundleData().putInt(key, value)
        return this
    }

    fun putString(key: String, value: String): Router {
        getBundleData().putString(key, value)
        return this
    }

    fun putShort(key: String, value: Short): Router {
        getBundleData().putShort(key, value)
        return this
    }

    fun putFloat(key: String, value: Float): Router {
        getBundleData().putFloat(key, value)
        return this
    }

    fun putCharSequence(key: String, value: CharSequence): Router {
        getBundleData().putCharSequence(key, value)
        return this
    }


    fun putParcelable(key: String, value: Parcelable): Router {
        getBundleData().putParcelable(key, value)
        return this
    }

    fun putParcelableArray(key: String, value: Array<out Parcelable>): Router {
        getBundleData().putParcelableArray(key, value)
        return this
    }


    fun putParceableArrayList(key: String, value: ArrayList<out Parcelable>): Router {
        getBundleData().putParcelableArrayList(key, value)
        return this
    }

    fun putIntegerArrayList(key: String, value: ArrayList<Int>): Router {
        getBundleData().putIntegerArrayList(key, value)
        return this
    }

    fun putStringArrayList(key: String, value: ArrayList<String>): Router {
        getBundleData().putStringArrayList(key, value)
        return this
    }

    fun putSerializable(key: String, value: Serializable): Router {
        getBundleData().putSerializable(key, value)
        return this
    }

    fun options(optionsCompat: ActivityOptionsCompat): Router {
        this.options = optionsCompat
        return this
    }

    fun request(requestCode: Int): Router {
        this.requestCode = requestCode
        return this
    }

    fun anim(enterAnim: Int, exitAnim: Int): Router {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
        return this
    }

    fun launch() {
        try {
            if (intent != null && from != null && to != null) {

                if (callback != null) {
                    Router.callback?.onBefore(from, to);
                }

                intent?.setClass(from, to)
                intent?.putExtras(getBundleData())

                if (options == null) {
                    if (requestCode < 0) {
                        from?.startActivity(intent);
                    } else {
                        from?.startActivityForResult(intent, requestCode);
                    }

                    if (enterAnim > 0 && exitAnim > 0) {
                        from?.overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    if (requestCode < 0) {
                        ActivityCompat.startActivity(from, intent, options?.toBundle())
                    } else {
                        ActivityCompat.startActivityForResult(from, intent, requestCode, options?.toBundle());
                    }
                }

                if (callback != null) {
                    callback?.onNext(from, to);
                }
            }
        } catch (throwable: Throwable) {
            if (callback != null) {
                callback?.onError(from, to, throwable);
            }
        }
    }

    private fun getBundleData(): Bundle {
        if (data == null) {
            data = Bundle()
        }

        return data as Bundle
    }


}
