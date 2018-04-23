package io.dwak.hoverlauncher.data.db.dao

import android.arch.persistence.room.*
import io.dwak.hoverlauncher.data.db.model.DbAppInfo
import io.reactivex.Flowable

@Dao
interface AppInfoDao {
  @Query("SELECT * from appInfo")
  fun getAll(): Flowable<List<DbAppInfo>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(dbAppInfo: DbAppInfo)

  @Delete
  fun delete(dbAppInfo: DbAppInfo)
}