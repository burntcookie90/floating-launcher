package io.dwak.hoverlauncher.data.android

interface PreferenceProvider {
  fun setFirstLaunch(firstLaunch: Boolean)
  suspend fun firstLaunch(): Boolean
}