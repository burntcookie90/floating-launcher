package io.dwak.hoverlauncher.data.android

import android.content.SharedPreferences
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import kotlin.coroutines.experimental.suspendCoroutine

class AndroidPreferenceProvider @Inject constructor(private val sharedPreferences: SharedPreferences)
  : PreferenceProvider {

  companion object {
    private const val FIRST_LAUNCH = "FIRST_LAUNCH"
  }

  override fun setFirstLaunch(firstLaunch: Boolean) {
    sharedPreferences.edit()
        .putBoolean(FIRST_LAUNCH, firstLaunch)
        .apply()
  }

  override suspend fun firstLaunch() = suspendCoroutine<Boolean> {
    async {
      it.resume(sharedPreferences.getBoolean(FIRST_LAUNCH, true))
    }
  }
}