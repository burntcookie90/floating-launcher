package io.dwak.hoverlauncher.app

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.dwak.hoverlauncher.data.modifier.Modification
import io.dwak.hoverlauncher.data.modifier.Modifier
import io.dwak.hoverlauncher.data.repo.AppInfoRepo
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import io.dwak.hoverlauncher.util.Mutable
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.channels.consume
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.SelectBuilder
import kotlinx.coroutines.experimental.selects.SelectClause1
import kotlinx.coroutines.experimental.selects.select
import kotlinx.coroutines.experimental.selects.whileSelect
import kotlinx.coroutines.experimental.yield
import timber.log.Timber
import javax.inject.Inject

class LauncherViewModel @Inject constructor(private val appInfoRepo: AppInfoRepo,
                                            private val modifier: Modifier) : ViewModel() {

  private val _mutableAppInfos: MutableLiveData<List<UiAppInfo>> by lazy {
    MutableLiveData<List<UiAppInfo>>()
  }
  private val _mutableInstalledAppInfo: MutableLiveData<List<UiAppInfo>> by lazy {
    MutableLiveData<List<UiAppInfo>>()
  }

  val appInfos: LiveData<List<UiAppInfo>> by Mutable(_mutableAppInfos)
  val installedAppInfos: LiveData<List<UiAppInfo>> by Mutable(_mutableInstalledAppInfo)

  fun getAppsToLaunch() = launch {
    val savedAppInfo = appInfoRepo.getSavedAppInfo()
    for (event in savedAppInfo) {
      _mutableAppInfos.postValue(event)
    }
  }

  fun getInstalledApps() {
    launch {
      val installedAppInfo = appInfoRepo.getInstalledAppInfo()
      _mutableInstalledAppInfo.postValue(installedAppInfo)
    }
  }

  fun saveAsAppToLaunch(uiAppInfo: UiAppInfo) {
    modifier.submit(Modification.SaveAppInfo(uiAppInfo))
  }
}