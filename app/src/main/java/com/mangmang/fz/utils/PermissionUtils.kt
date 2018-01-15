package com.mangmang.fz.utils

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings

/**
 * Created by mangmang on 2017/10/31.
 */
object PermissionUtils {

    fun goSetting(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }


    fun checkPermission(context: Activity, permissionStr: String): Boolean {
        if (permissionStr.isEmpty()) return false
        //判断是真的有给权限。。。  国产手机坑货
        try {
            val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val opstr = when (permissionStr) {
                Manifest.permission.READ_CALL_LOG -> {
                    AppOpsManager.OPSTR_READ_CALL_LOG
                }
                Manifest.permission.READ_CONTACTS -> {
                    AppOpsManager.OPSTR_READ_CONTACTS
                }
                Manifest.permission.CAMERA -> {
                    AppOpsManager.OPSTR_CAMERA

                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    AppOpsManager.OPSTR_FINE_LOCATION
                }
                else -> ""
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val permissionCode = appOpsManager.checkOp(opstr, Binder.getCallingUid(), context.packageName)
                if (permissionCode == AppOpsManager.MODE_ALLOWED) return true
            }
        } catch (e: Exception) {
            return true
        }


        return false
    }

}