package com.mangmang.fz.di.module

import com.mangmang.fz.ui.fragment.DynamicFragment
import com.mangmang.fz.ui.fragment.PhotoFragment
import com.mangmang.fz.ui.fragment.user.UserAlbumFragment
import com.mangmang.fz.ui.fragment.user.UserProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by mangmang on 2017/9/26.
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun dynamicFragmentInjector(): DynamicFragment

    @ContributesAndroidInjector
    abstract fun userProfileInjector(): UserProfileFragment

    @ContributesAndroidInjector
    abstract fun photoFragmentInjector(): PhotoFragment

    @ContributesAndroidInjector
    abstract fun userAlbumFragmentInjector(): UserAlbumFragment

}