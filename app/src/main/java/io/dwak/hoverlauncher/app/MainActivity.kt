package io.dwak.hoverlauncher.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.dwak.hoverlauncher.R
import io.dwak.hoverlauncher.data.android.InstalledPackageProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotterknife.bindView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var viewModelFactory: InjectionViewModelFactory
  @Inject lateinit var installedPackageProvider: InstalledPackageProvider
  lateinit var launcherViewModel: LauncherViewModel

  private val addFab : FloatingActionButton by bindView(R.id.add_fab)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    AndroidInjection.inject(this)
    launcherViewModel = ViewModelProviders.of(this, viewModelFactory)[LauncherViewModel::class.java]

    launcherViewModel.appInfos
        .observe(this, Observer {

        })

    launcherViewModel.getAppsToLaunch()

    addFab.setOnClickListener {
      launch {
        val installedApps = installedPackageProvider.getInstalledApps()
        withContext(UI) {
          Timber.d("$installedApps")
        }
      }
    }

  }
}
