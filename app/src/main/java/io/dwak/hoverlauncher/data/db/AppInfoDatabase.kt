package io.dwak.hoverlauncher.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.dwak.hoverlauncher.data.db.dao.AppInfoDao
import io.dwak.hoverlauncher.data.db.model.DbAppInfo

@Database(entities = [DbAppInfo::class], version = 2)
abstract class AppInfoDatabase : RoomDatabase(){
  abstract fun appInfoDao(): AppInfoDao
}