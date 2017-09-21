package com.mangmang.fz.di.module

import com.mangmang.fz.ui.act.LoginAtivity
import com.mangmang.fz.ui.act.MainActivity
import com.mangmang.fz.ui.act.TestActivity
import com.mangmang.fz.ui.act.WelcomeAct
import com.mangmang.fz.ui.fragment.DynamicFragment
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
    abstract fun dyActInjector(): DynamicFragment

}