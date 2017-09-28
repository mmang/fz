package com.mangmang.fz.di.component

import com.mangmang.fz.FZApplication
import com.mangmang.fz.di.module.ApiModule
import com.mangmang.fz.di.module.AppModule
import com.mangmang.fz.di.module.BuildersModule
import com.mangmang.fz.di.module.FragmentModule
import com.mangmang.fz.glide.OkHttpStreamFetcher
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        BuildersModule::class,
        ApiModule::class,
        FragmentModule::class,
        AndroidSupportInjectionModule::class))
interface AppComponent {

//    @Component.Builder
//    abstract class Bulider : AndroidInjector.Builder<FZApplication>() {}
    fun inject(fzApplication: FZApplication)
    fun inject(okHttpStreamFetcher: OkHttpStreamFetcher)

    fun provideOkHttp():OkHttpClient
}