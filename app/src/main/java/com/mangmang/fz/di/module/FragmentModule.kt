package com.mangmang.fz.di.module

import com.mangmang.fz.ui.fragment.*
import com.mangmang.fz.ui.fragment.user.GuestFragment
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

    @ContributesAndroidInjector
    abstract fun LogFragment(): LogFragment

    @ContributesAndroidInjector
    abstract fun NearbyFragment(): NearbyFragment

    @ContributesAndroidInjector
    abstract fun MineFragment(): MineFragment

    @ContributesAndroidInjector
    abstract fun guestFragment(): GuestFragment

}