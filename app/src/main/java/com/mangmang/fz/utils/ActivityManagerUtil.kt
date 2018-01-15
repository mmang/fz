package com.mangmang.fz.utils

import android.app.Activity
import android.os.Process
import java.util.*

/**
 * Created by mangmang on 2017/9/14.
 */
object ActivityManagerUtil{
    private val activityStack = Stack<Activity>()


    fun pushOneActivity(actvity: Activity) {
        this.activityStack.add(actvity)
    }

    fun popOneActivity(activity: Activity?) {
        if (this.activityStack != null && this.activityStack.size > 0 && activity != null) {
            this.activityStack.remove(activity)
            activity.finish()
        }

    }

    fun getLastActivity(): Activity {
        return this.activityStack.lastElement()
    }

    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            this.activityStack.remove(activity)
            activity.finish()
        }

    }

    fun finishActivity(cls: Class<*>) {
        val var2 = this.activityStack.iterator()

        while (var2.hasNext()) {
            val activity = var2.next() as Activity
            if (activity.javaClass == cls) {
                this.finishActivity(activity)
            }
        }

    }

    fun finishAllActivity() {
        try {
            for (i in this.activityStack.indices) {
                if (null != this.activityStack.get(i)) {
                    (this.activityStack.get(i) as Activity).finish()
                }
            }

            this.activityStack.clear()
        } catch (var2: Exception) {
            var2.printStackTrace()
        }

    }

    fun appExit() {
        try {
            this.finishAllActivity()
            System.exit(0)
            Process.killProcess(Process.myPid())
        } catch (var2: Exception) {
            var2.printStackTrace()
        }

    }
}