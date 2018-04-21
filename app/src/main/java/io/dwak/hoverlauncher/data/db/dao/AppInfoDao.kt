package io.dwak.hoverlauncher.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.dwak.hoverlauncher.data.db.model.DbAppInfo

@Dao
interface AppInfoDao {
  @Query("SELECT * from `app-info`")
  fun getAll(): List<DbAppInfo>
}