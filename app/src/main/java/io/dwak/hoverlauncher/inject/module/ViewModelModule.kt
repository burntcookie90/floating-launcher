package io.dwak.hoverlauncher.inject.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.dwak.hoverlauncher.app.InjectionViewModelFactory
import io.dwak.hoverlauncher.app.LauncherViewModel

@Module
abstract class ViewModelModule {
  @Binds
  abstract fun bindFactory(daggerViewModelFactory: InjectionViewModelFactory): ViewModelProvider.Factory

  @IntoMap @ClassKey(LauncherViewModel::class)
  @Binds abstract fun bindLauncherViewModel(launcherViewModel: LauncherViewModel): ViewModel

}