package io.dwak.hoverlauncher.inject.module

import dagger.Binds
import dagger.Module
import io.dwak.hoverlauncher.data.android.AndroidInstalledPackageProvider
import io.dwak.hoverlauncher.data.android.InstalledPackageProvider

@Module
abstract class ProviderModule {
  @Binds abstract fun bindsInstalledPackageProvider(provider: AndroidInstalledPackageProvider)
      : InstalledPackageProvider
}