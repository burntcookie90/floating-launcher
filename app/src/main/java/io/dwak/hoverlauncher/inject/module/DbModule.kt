package io.dwak.hoverlauncher.inject.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.dwak.hoverlauncher.data.db.AppInfoDatabase
import javax.inject.Singleton

@Module
class DbModule {
  @Provides @Singleton fun db(context: Context): AppInfoDatabase =
      Room.databaseBuilder(context, AppInfoDatabase::class.java, "app-info").build()

  @Provides @Singleton fun launcherDao(appInfoDatabase: AppInfoDatabase) = appInfoDatabase.appInfoDao()
}