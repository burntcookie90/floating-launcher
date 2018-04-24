package io.dwak.hoverlauncher.app

import android.content.Intent
import dagger.android.AndroidInjection
import io.mattcarroll.hover.HoverView
import io.mattcarroll.hover.window.HoverMenuService
import javax.inject.Inject

class LauncherMenuService : HoverMenuService(){
  @Inject
  lateinit var launcherViewModel: LauncherViewModel

  override fun onCreate() {
    super.onCreate()
    AndroidInjection.inject(this)
  }

  override fun onHoverMenuLaunched(intent: Intent, hoverView: HoverView) {
    val menu = LauncherMenu(launcherViewModel, this)
    hoverView.setMenu(menu)
    hoverView.collapse()
  }
}