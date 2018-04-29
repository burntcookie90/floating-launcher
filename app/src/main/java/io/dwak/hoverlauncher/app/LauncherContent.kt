package io.dwak.hoverlauncher.app

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import io.dwak.hoverlauncher.R
import io.mattcarroll.hover.Content

class LauncherContent(private val context: Context,
                      private val launcherViewModel: LauncherViewModel,
                      private val lifecycleOwner: LifecycleOwner,
                      private val onClick: () -> Unit) : Content {
  private val packageManage = context.packageManager

  override fun getView(): View {
    val wholeScreen = RecyclerView(context)
    wholeScreen.setBackgroundColor(context.getColor(R.color.white))
    wholeScreen.layoutManager = LinearLayoutManager(context)
    val appInfoAdapter = AppInfoAdapter({
      context.startActivity(packageManage.getLaunchIntentForPackage(it.appId))
      onClick()
    })
    wholeScreen.adapter = appInfoAdapter
    launcherViewModel.appInfos
        .observeForever({
//        .observe(lifecycleOwner, Observer {
          appInfoAdapter.submitList(it)
        })
    return wholeScreen
  }

  override fun onShown() = Unit
  override fun onHidden() = Unit
  override fun isFullscreen() = false
}
