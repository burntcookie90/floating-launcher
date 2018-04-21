package io.dwak.hoverlauncher.app

import android.arch.lifecycle.ViewModel
import io.dwak.hoverlauncher.data.repo.AppInfoRepo
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import kotlin.coroutines.experimental.suspendCoroutine

class LauncherViewModel @Inject constructor(private val appInfoRepo: AppInfoRepo): ViewModel() {
  suspend fun getAppsToLaunch() = suspendCoroutine<List<UiAppInfo>> {
    async {
      appInfoRepo.getSavedAppInfo()
    }
  }
}