package io.dwak.hoverlauncher.inject.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.dwak.hoverlauncher.app.HoverLauncherApplication

@Module
class AppModule {
  @Provides
  fun context(app: HoverLauncherApplication): Context = app.applicationContext

}