package io.dwak.hoverlauncher.app

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.dwak.hoverlauncher.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var viewModelFactory: InjectionViewModelFactory
  lateinit var launcherViewModel: LauncherViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    AndroidInjection.inject(this)
    launcherViewModel = ViewModelProviders.of(this, viewModelFactory)[LauncherViewModel::class.java]

    launch(UI) {
      launcherViewModel.getAppsToLaunch()
      Timber.d("got apps")
    }
  }
}
