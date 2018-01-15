package com.mangmang.fz

import android.content.Context
import com.mangmang.fz.ui.dialog.LoadingDialog

/**
 * Created by mangmang on 2017/8/1.
 */
object DialogManager {

    var loadDialog: LoadingDialog? = null

    fun showLoadDialog(context: Context) {
        if (loadDialog == null)
            loadDialog = LoadingDialog(context)
        loadDialog!!.show()
    }

    fun dissLoadDialog() {
        if (loadDialog != null) {
            loadDialog?.dismiss()
        }
        loadDialog = null
    }
}