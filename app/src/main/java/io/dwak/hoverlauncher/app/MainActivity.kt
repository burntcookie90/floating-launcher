package io.dwak.hoverlauncher.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.idescout.sql.SqlScoutServer
import dagger.android.AndroidInjection
import io.dwak.hoverlauncher.R
import io.dwak.hoverlauncher.data.modifier.Modifier
import io.dwak.hoverlauncher.data.ui.model.UiAppInfo
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import kotterknife.bindView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var viewModelFactory: InjectionViewModelFactory
  @Inject lateinit var modifier: Modifier
  lateinit var launcherViewModel: LauncherViewModel
  lateinit var adapter: AppInfoAdapter

  private val addFab : FloatingActionButton by bindView(R.id.add_fab)
  private val appInfoList: RecyclerView by bindView(R.id.app_list)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SqlScoutServer.create(this, packageName)
    setContentView(R.layout.activity_main)
    AndroidInjection.inject(this)
    launcherViewModel = ViewModelProviders.of(this, viewModelFactory)[LauncherViewModel::class.java]

    appInfoList.layoutManager = LinearLayoutManager(this)
    appInfoList.adapter = AppInfoAdapter(modifier).also { this.adapter = it }

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

  suspend fun showAppPicker(installedApps: List<UiAppInfo>) = suspendCancellableCoroutine<UiAppInfo> {
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
}
