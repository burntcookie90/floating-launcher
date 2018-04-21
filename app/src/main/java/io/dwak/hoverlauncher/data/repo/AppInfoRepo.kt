package io.dwak.hoverlauncher.data.repo

import io.dwak.hoverlauncher.data.db.dao.AppInfoDao
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.suspendCoroutine

@Singleton
class AppInfoRepo @Inject constructor(private val appInfoDao: AppInfoDao) : Repo<UiAppInfo> {
  suspend fun getSavedAppInfo() = suspendCoroutine<List<UiAppInfo>> {
    appInfoDao.getAll()
        .map {
          UiAppInfo(it.appId, it.appName, it.appIconResId)
        }
  }
}