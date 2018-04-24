package io.dwak.hoverlauncher.inject.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.dwak.hoverlauncher.app.LauncherMenuService
import io.dwak.hoverlauncher.app.MainActivity
import javax.inject.Singleton

@Module
abstract class ServiceModule {
  @Singleton
  @ContributesAndroidInjector(modules = [
    DbModule::class, ModifierModule::class, ProviderModule::class, ViewModelModule::class
  ])
  abstract fun contributesLauncherService(): LauncherMenuService
}