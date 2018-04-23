package io.dwak.hoverlauncher.inject.module

import dagger.Binds
import dagger.Module
import io.dwak.hoverlauncher.data.modifier.DbModifier
import io.dwak.hoverlauncher.data.modifier.Modifier

@Module
abstract class ModifierModule {
  @Binds abstract fun bindModifier(dbModifier: DbModifier): Modifier
}