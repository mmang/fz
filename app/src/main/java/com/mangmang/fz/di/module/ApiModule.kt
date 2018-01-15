package com.mangmang.fz.di.module

import com.mangmang.fz.FZApplication
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.net.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by mangmang on 2017/9/14.
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return RetrofitClient.getInstance(FZApplication.getsInstance()).okHttpClient
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.getInstance(FZApplication.getsInstance())
                .create(ApiService::class.java)!!
    }
}