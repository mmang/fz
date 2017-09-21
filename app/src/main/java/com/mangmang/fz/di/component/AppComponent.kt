package com.mangmang.fz.di.component

import com.mangmang.fz.FZApplication
import com.mangmang.fz.di.module.ApiModule
import com.mangmang.fz.di.module.AppModule
import com.mangmang.fz.di.module.BuildersModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        BuildersModule::class,
        ApiModule::class,
        AndroidSupportInjectionModule::class))
interface AppComponent : AndroidInjector<FZApplication> {

    @Component.Builder
    abstract class Bulider : AndroidInjector.Builder<FZApplication>(){}
}