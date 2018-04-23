package io.dwak.hoverlauncher.app

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo

class AppInfoAdapter(private val modifier: io.dwak.hoverlauncher.data.modifier.Modifier): ListAdapter<UiAppInfo, AppInfoViewHolder>(AppInfoDiffer()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder =
      AppInfoViewHolder(parent, modifier)

  override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class AppInfoDiffer: DiffUtil.ItemCallback<UiAppInfo>() {
    override fun areItemsTheSame(oldItem: UiAppInfo, newItem: UiAppInfo): Boolean =
        oldItem.appId == newItem.appId

    override fun areContentsTheSame(oldItem: UiAppInfo, newItem: UiAppInfo): Boolean =
        oldItem == newItem
  }
}