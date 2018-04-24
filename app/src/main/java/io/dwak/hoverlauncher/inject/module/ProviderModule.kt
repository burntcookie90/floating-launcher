package io.dwak.hoverlauncher.inject.module

import dagger.Binds
import dagger.Module
import io.dwak.hoverlauncher.data.android.AndroidInstalledPackageProvider
import io.dwak.hoverlauncher.data.android.AndroidPreferenceProvider
import io.dwak.hoverlauncher.data.android.InstalledPackageProvider
import io.dwak.hoverlauncher.data.android.PreferenceProvider

@Module
abstract class ProviderModule {
  @Binds abstract fun bindsInstalledPackageProvider(provider: AndroidInstalledPackageProvider)
      : InstalledPackageProvider

  @Binds abstract fun bindsPreferenceProvider(provider: AndroidPreferenceProvider)
      : PreferenceProvider
}