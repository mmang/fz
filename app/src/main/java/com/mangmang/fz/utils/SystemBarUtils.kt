package com.mangmang.fz

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

/**
 * Created by mangmang on 2017/7/10.
 */
class SystemBarUtils {


    companion object {
        // 全屏 有navbar
        fun fullScreen(window: Window) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏flag
            decorView.systemUiVisibility = option
        }

        //全屏 透明的状态栏
        fun fullScreenTransparentStatusBar(window: Window) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            if (Build.VERSION.SDK_INT >= 21)
                window.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = option
        }

        //
        //全屏 透明状态栏和 隐藏导航栏  点击之后会出现导航栏
        fun fullScreenTransStatusHideNavBar(window: Window) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            if (Build.VERSION.SDK_INT >= 21) {
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //设置4.4的
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            }
            decorView.systemUiVisibility = option
        }

        // 全屏 透明导航栏 和状态栏
        fun fullScreenTransStatusNavBar(window: Window) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            if (Build.VERSION.SDK_INT >= 21) {
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
            }
            decorView.systemUiVisibility = option
        }

        fun realFullScreen(window: Window) {
            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                fullScreen(window)
            }
        }
    }
}