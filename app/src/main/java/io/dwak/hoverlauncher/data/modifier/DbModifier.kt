package io.dwak.hoverlauncher.data.modifier

import io.dwak.hoverlauncher.data.db.dao.AppInfoDao
import io.dwak.hoverlauncher.data.db.model.DbAppInfo
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class DbModifier @Inject constructor(private val appInfoDao: AppInfoDao) : Modifier {
  override fun submit(modification: Modification) {
    when (modification) {
      is Modification.SaveAppInfo -> {
        launch {
          appInfoDao.insert(modification.info.toDb())
        }
      }
      is Modification.DeleteAppInfo -> {
        launch {
          appInfoDao.delete(modification.info.toDb())
        }
      }
    }
  }

  private fun UiAppInfo.toDb() = DbAppInfo(appId, appName, appIconResId)
}