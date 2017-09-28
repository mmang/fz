package com.mangmang.fz

import android.app.Activity
import android.app.Application
import com.mangmang.fz.di.component.AppComponent
import com.mangmang.fz.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by mangmang on 2017/8/28.
 */
class FZApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }


    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
        instance = this
    }


    fun getAppComponent(): AppComponent {
        return DaggerAppComponent.create()
    }

    companion object {
        @JvmStatic lateinit var instance: FZApplication
        fun getsInstance(): FZApplication {
            return instance
        }
    }

}