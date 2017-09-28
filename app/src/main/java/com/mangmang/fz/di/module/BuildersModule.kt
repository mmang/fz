package com.mangmang.fz.di.module

import com.mangmang.fz.glide.OkHttpStreamFetcher
import com.mangmang.fz.ui.act.*
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.act.photo.PhotoPreActivity
import com.mangmang.fz.ui.fragment.DynamicFragment
import com.mangmang.fz.ui.fragment.PhotoFragment
import com.mangmang.fz.ui.fragment.user.UserProfileFragment
import dagger.Module
import dagger.Subcomponent
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

}