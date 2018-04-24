package io.dwak.hoverlauncher.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import dagger.android.AndroidInjection
import io.dwak.hoverlauncher.R
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import kotterknife.bindView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var viewModelFactory: InjectionViewModelFactory
  private lateinit var launcherViewModel: LauncherViewModel
  private lateinit var adapter: AppInfoAdapter

  private val addFab: FloatingActionButton by bindView(R.id.add_fab)
  private val appInfoList: RecyclerView by bindView(R.id.app_list)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    AndroidInjection.inject(this)
    launcherViewModel = ViewModelProviders.of(this, viewModelFactory)[LauncherViewModel::class.java]

    launch(UI) {
      if (launcherViewModel.firstLaunch()) {
        if (showConfirmationDialog(this@MainActivity,
                "Open System Settings for App",
                "The app requires the overlay permission, open settings?")) {
          startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }
        else {
          finish()
        }

        launcherViewModel.setFirstLaunch(false)
      }
    }

    appInfoList.layoutManager = LinearLayoutManager(this)
    appInfoList.adapter = AppInfoAdapter({
      launch(UI) {
        if (showConfirmationDialog(this@MainActivity, "Delete ${it.appName}?")) {
          launcherViewModel.deleteAppToLaunch(it)
        }
      }
    }).also { this.adapter = it }

    launcherViewModel.appInfos
        .observe(this, Observer {
          Timber.d("on Activity: $it")
          adapter.submitList(it)
        })

    launcherViewModel.installedAppInfos
        .observe(this, Observer {
          launch(UI) {
            launcherViewModel.saveAsAppToLaunch(showAppPicker(requireNotNull(it)))
          }
        })

    launcherViewModel.getAppsToLaunch()

    addFab.setOnClickListener {
      launcherViewModel.getInstalledApps()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_activity_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.launch -> {
        startService(Intent(this@MainActivity, LauncherMenuService::class.java))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private suspend fun showAppPicker(installedApps: List<UiAppInfo>) = suspendCancellableCoroutine<UiAppInfo> {
    val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        .setTitle("Select an app")

    val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item)
    arrayAdapter.addAll(installedApps.map { it.appName })
    dialogBuilder.setAdapter(arrayAdapter, { _, i ->
      it.resume(installedApps[i])
    })

    val dialog = dialogBuilder.create().also { it.show() }

    it.invokeOnCompletion { dialog.dismiss() }
  }

  private suspend fun showConfirmationDialog(context: Context, title: String, message: String? = null) =
      suspendCancellableCoroutine<Boolean> {
        val dialogBuilder = AlertDialog.Builder(context)
            .setTitle(title)
            .setPositiveButton("Yes", { _, _ -> it.resume(true) })
            .setNegativeButton("No", { _, _ -> it.resume(false) })
            .setCancelable(true)
            .setOnCancelListener { _ -> it.resume(false) }

        if (message != null) {
          dialogBuilder.setMessage(message)
        }

        val dialog = dialogBuilder.create()

        it.invokeOnCompletion { dialog.dismiss() }

        dialog.show()
      }
}
