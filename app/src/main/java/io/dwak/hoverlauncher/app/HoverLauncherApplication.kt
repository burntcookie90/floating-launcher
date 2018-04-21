package io.dwak.hoverlauncher.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.dwak.hoverlauncher.inject.component.DaggerAppComponent
import timber.log.Timber

class HoverLauncherApplication: DaggerApplication() {
  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
  }
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
      DaggerAppComponent.builder().create(this)
}