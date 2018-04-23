package io.dwak.hoverlauncher.app

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import io.dwak.hoverlauncher.R
import io.dwak.hoverlauncher.data.modifier.Modification
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotterknife.bindView
import kotlin.coroutines.experimental.suspendCoroutine

class AppInfoViewHolder(parent: ViewGroup,
                        private val modifier: io.dwak.hoverlauncher.data.modifier.Modifier)
  : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_app_info, parent, false)) {
  private val appName: TextView by bindView(R.id.app_name)

  private lateinit var uiAppInfo: UiAppInfo

  init {
    itemView.setOnClickListener {
      launch(UI) {
        if (showDeleteConfirmationDialog(itemView.context, uiAppInfo)) {
          modifier.submit(Modification.DeleteAppInfo(uiAppInfo))
        }
      }
    }
  }

  fun bind(uiAppInfo: UiAppInfo) {
    this.uiAppInfo = uiAppInfo
    appName.text = uiAppInfo.appName
  }

  private suspend fun showDeleteConfirmationDialog(context: Context,
                                                   uiAppInfo: UiAppInfo) = suspendCoroutine<Boolean> {
    AlertDialog.Builder(context)
        .setTitle("Delete ${uiAppInfo.appName}?")
        .setPositiveButton("Yes", { _, _ -> it.resume(true) })
        .setNegativeButton("No", { _, _ -> it.resume(false) })
        .setCancelable(true)
        .setOnCancelListener { _ -> it.resume(false) }
        .show()
  }
}