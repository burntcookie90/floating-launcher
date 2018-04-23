package io.dwak.hoverlauncher.data.repo

import io.dwak.hoverlauncher.data.android.InstalledPackageProvider
import io.dwak.hoverlauncher.data.db.dao.AppInfoDao
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.SubscriptionReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.reactive.openSubscription
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.suspendCoroutine

@Singleton
class AppInfoRepo @Inject constructor(private val appInfoDao: AppInfoDao,
                                      private val installedPackageProvider: InstalledPackageProvider)
  : Repo {
  fun getSavedAppInfo(): SubscriptionReceiveChannel<List<UiAppInfo>> {
    return appInfoDao.getAll()
        .subscribeOn(Schedulers.io())
        .map { it.map { UiAppInfo(it.appId, it.appName, it.appIconResId) } }
        .doOnNext { Timber.d("From Repo: $it") }
        .openSubscription()
  }

  suspend fun getInstalledAppInfo() = suspendCoroutine<List<UiAppInfo>> {
    launch {
      it.resume(
          installedPackageProvider.getInstalledApps()
              .map { UiAppInfo(it.appId, it.appName, it.iconResId) }
      )
    }
  }
}