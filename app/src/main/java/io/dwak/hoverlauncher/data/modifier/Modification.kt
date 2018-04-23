package io.dwak.hoverlauncher.data.modifier

import io.dwak.hoverlauncher.data.ui.model.UiAppInfo

sealed class Modification {
  data class SaveAppInfo(val info: UiAppInfo): Modification()
  data class DeleteAppInfo(val info: UiAppInfo): Modification()
}