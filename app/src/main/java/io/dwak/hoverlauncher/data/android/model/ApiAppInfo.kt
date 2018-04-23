package io.dwak.hoverlauncher.data.android.model

import android.support.annotation.DrawableRes

data class ApiAppInfo(val appId: String, val appName: String, @DrawableRes val iconResId: Int)
