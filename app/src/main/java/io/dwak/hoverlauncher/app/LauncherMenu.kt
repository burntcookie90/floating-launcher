package io.dwak.hoverlauncher.app

import android.content.Context
import io.mattcarroll.hover.HoverMenu
import javax.inject.Inject


class LauncherMenu(private val context: Context,
                   private val launcherViewModel: LauncherViewModel): HoverMenu() {

  override fun getSections(): MutableList<Section> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getId(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSection(index: Int): Section? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSection(sectionId: SectionId): Section? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSectionCount(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}