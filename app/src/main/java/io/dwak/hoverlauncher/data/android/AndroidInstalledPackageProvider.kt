package io.dwak.hoverlauncher.data.android

import android.content.Context
import io.dwak.hoverlauncher.data.android.model.ApiAppInfo
import javax.inject.Inject
import kotlin.coroutines.experimental.suspendCoroutine

class AndroidInstalledPackageProvider @Inject constructor(private val context: Context)
  : InstalledPackageProvider {

  override suspend fun getInstalledApps()= suspendCoroutine<List<ApiAppInfo>> {
    val packageManager = context.packageManager
    it.resume(
        packageManager.getInstalledApplications(0)
            .map {
              ApiAppInfo(it.packageName,
                  it.loadLabel(packageManager).toString(),
                  it.icon)
            }
    )
  }
}