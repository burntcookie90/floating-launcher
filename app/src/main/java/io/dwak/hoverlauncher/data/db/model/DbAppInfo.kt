package io.dwak.hoverlauncher.data.db.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "appInfo")
data class DbAppInfo(@PrimaryKey var appId: String,
                     var appName: String,
                     var appIconResId: Int)
