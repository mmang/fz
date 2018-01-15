package com.mangmang.fz.di.module

import com.mangmang.fz.ui.act.*
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.act.photo.PhotoPreActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by mangmang on 2017/9/15.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun testActInjector(): TestActivity

    @ContributesAndroidInjector
    abstract fun mainActInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun loginActInjector(): LoginAtivity

    @ContributesAndroidInjector
    abstract fun welcomeActInjector(): WelcomeAct

    @ContributesAndroidInjector
    abstract fun userDetailActInjector(): UserDetailActivity

    @ContributesAndroidInjector
    abstract fun photoListActivity(): PhotoListActivity

    @ContributesAndroidInjector
    abstract fun photoPreActivity(): PhotoPreActivity

    @ContributesAndroidInjector
    abstract fun TopicDetailActivity (): TopicDetailActivity

}