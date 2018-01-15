package com.happyfi.lelerong.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.mangmang.fz.net.ApiService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/18.
 * 需要 使用dagger注入的基类
 */
abstract class BaseCommonActivity : BaseActivity(),
        HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var apiService: ApiService

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return frameworkFragmentInjector
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


}