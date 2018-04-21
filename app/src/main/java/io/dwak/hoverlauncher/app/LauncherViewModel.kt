package io.dwak.hoverlauncher.app

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.dwak.hoverlauncher.data.repo.AppInfoRepo
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import io.dwak.hoverlauncher.util.Mutable
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class LauncherViewModel @Inject constructor(private val appInfoRepo: AppInfoRepo) : ViewModel() {

  private val _mutableAppInfos: MutableLiveData<List<UiAppInfo>> by lazy {
    MutableLiveData<List<UiAppInfo>>()
  }

  val appInfos: LiveData<List<UiAppInfo>> by Mutable(_mutableAppInfos)

  fun getAppsToLaunch() {
    launch {
      _mutableAppInfos.postValue(appInfoRepo.getSavedAppInfo())
    }
  }
}