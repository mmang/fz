package com.mangmang.fz

import android.app.Application
import com.mangmang.fz.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by mangmang on 2017/8/28.
 */
class FZApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @JvmStatic lateinit var instance: FZApplication
        fun getsInstance(): FZApplication {
            return instance
        }
    }

}