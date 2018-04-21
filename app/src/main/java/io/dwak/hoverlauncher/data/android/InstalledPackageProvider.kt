package io.dwak.hoverlauncher.data.android

import io.dwak.hoverlauncher.data.android.model.ApiAppInfo

interface InstalledPackageProvider {
  suspend fun getInstalledApps(): List<ApiAppInfo>
}