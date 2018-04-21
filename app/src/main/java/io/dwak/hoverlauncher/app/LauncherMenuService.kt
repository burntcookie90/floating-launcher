package io.dwak.hoverlauncher.app

import android.content.Intent
import io.mattcarroll.hover.HoverView
import io.mattcarroll.hover.window.HoverMenuService

class LauncherMenuService : HoverMenuService(){

  override fun onHoverMenuLaunched(intent: Intent, hoverView: HoverView) {
    super.onHoverMenuLaunched(intent, hoverView)
  }
}