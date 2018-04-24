package io.dwak.hoverlauncher.inject.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.dwak.hoverlauncher.app.HoverLauncherApplication
import io.dwak.hoverlauncher.inject.module.AppModule
import io.dwak.hoverlauncher.inject.module.ActivityModule
import io.dwak.hoverlauncher.inject.module.ServiceModule
import io.dwak.hoverlauncher.inject.module.ViewModelModule

@Component(modules = [
  ActivityModule::class,
  AndroidSupportInjectionModule::class,
  AppModule::class,
  ServiceModule::class,
  ViewModelModule::class
])
interface AppComponent : AndroidInjector<HoverLauncherApplication> {
  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<HoverLauncherApplication>()
}