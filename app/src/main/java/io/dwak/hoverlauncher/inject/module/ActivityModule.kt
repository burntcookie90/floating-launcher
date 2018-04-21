package io.dwak.hoverlauncher.inject.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.dwak.hoverlauncher.app.MainActivity
import javax.inject.Singleton

@Module
abstract class ActivityModule {
  @Singleton
  @ContributesAndroidInjector(modules = [DbModule::class, ProviderModule::class, ViewModelModule::class])
  abstract fun contributeYourActivityInjector(): MainActivity
}