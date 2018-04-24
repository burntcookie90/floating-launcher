package io.dwak.hoverlauncher.app

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.widget.ImageView
import io.dwak.hoverlauncher.R
import io.mattcarroll.hover.HoverMenu


class LauncherMenu(launcherViewModel: LauncherViewModel,
                   context: Context,
                   lifecycleOwner: LifecycleOwner,
                   onClick: () -> Unit)
  : HoverMenu() {

  private val section: Section by lazy {
    launcherViewModel.getAppsToLaunch()
    val launcherIcon = ImageView(context).apply {
      setImageResource(R.drawable.ic_launch_white_24dp)
      scaleType = ImageView.ScaleType.CENTER_INSIDE
      setBackgroundResource(R.drawable.circle)
    }
    val sectionId = SectionId("1")

    val content = HoverContent(context, launcherViewModel, lifecycleOwner, onClick)
    Section(sectionId, launcherIcon, content)
  }

  override fun getSections() = mutableListOf(section)
  override fun getId(): String = "single-section-menu"
  override fun getSection(index: Int): Section? = section
  override fun getSection(sectionId: SectionId): Section? = section
  override fun getSectionCount(): Int = 1

}