package io.dwak.hoverlauncher.app

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import io.dwak.hoverlauncher.R
import io.mattcarroll.hover.Content
import io.mattcarroll.hover.HoverMenu


class LauncherMenu(private val launcherViewModel: LauncherViewModel, context: Context) :
    HoverMenu() {

  private val section: Section
  private val packageManage = context.packageManager

  init {
    launcherViewModel.getAppsToLaunch()
    val launcherIcon = ImageView(context).apply {
      setImageResource(R.drawable.ic_launch_white_24dp)
      scaleType = ImageView.ScaleType.CENTER_INSIDE
      setBackgroundResource(R.drawable.circle)
    }
    section = Section(SectionId("1"), launcherIcon, object :
        Content {
      override fun getView(): View {
        val wholeScreen = RecyclerView(context)
        wholeScreen.setBackgroundColor(context.getColor(R.color.white))
        wholeScreen.layoutManager = LinearLayoutManager(context)
        val appInfoAdapter = AppInfoAdapter({
          context.startActivity(packageManage.getLaunchIntentForPackage(it.appId))
        })
        wholeScreen.adapter = appInfoAdapter
        launcherViewModel.appInfos.observeForever {
          appInfoAdapter.submitList(it)
        }
        return wholeScreen
      }

      override fun onShown() = Unit

      override fun onHidden() = Unit

      override fun isFullscreen() = false
    })
  }

  override fun getSections(): MutableList<Section> {
    return mutableListOf(section)
  }

  override fun getId(): String = "single-section-menu"

  override fun getSection(index: Int): Section? {
    return section
  }

  override fun getSection(sectionId: SectionId): Section? {
    return section
  }

  override fun getSectionCount(): Int = 1

}