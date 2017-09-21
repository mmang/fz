package com.mangmang.fz.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mangmang on 2017/9/14.
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(context: Context): Context {
        return context
    }
}