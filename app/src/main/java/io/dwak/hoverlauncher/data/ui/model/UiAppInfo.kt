package io.dwak.hoverlauncher.data.ui.model

import android.support.annotation.DrawableRes

data class UiAppInfo(val appId: String,
                     val appName: String,
                     @DrawableRes val appIconResId: Int)
