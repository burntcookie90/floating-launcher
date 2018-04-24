package io.dwak.hoverlauncher.app

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.dwak.hoverlauncher.R
import io.dwak.hoverlauncher.data.modifier.Modification
import io.dwak.hoverlauncher.data.modifier.Modifier
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotterknife.bindView
import kotlin.coroutines.experimental.suspendCoroutine

class AppInfoViewHolder(parent: ViewGroup,
                        private val onClick: (UiAppInfo) -> Unit)
  : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_app_info, parent, false)) {
  private val appName: TextView by bindView(R.id.app_name)
  private val appIcon: ImageView by bindView(R.id.app_icon)

  private lateinit var uiAppInfo: UiAppInfo

  init {
    itemView.setOnClickListener { onClick(uiAppInfo) }
  }

  fun bind(uiAppInfo: UiAppInfo) {
    this.uiAppInfo = uiAppInfo
    appName.text = uiAppInfo.appName
    val packageManager = itemView.context.packageManager
    val icon = packageManager.getApplicationIcon(uiAppInfo.appId)
    appIcon.setImageDrawable(icon)
  }
}