package io.dwak.hoverlauncher.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ServiceLifecycleDispatcher
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import dagger.android.AndroidInjection
import io.mattcarroll.hover.HoverView
import io.mattcarroll.hover.window.HoverMenuService
import timber.log.Timber
import javax.inject.Inject

class LauncherMenuService : HoverMenuService(), LifecycleOwner {
  private val lifecycleDispatcher = ServiceLifecycleDispatcher(this)


  @Inject
  lateinit var launcherViewModel: LauncherViewModel

  override fun onCreate() {
    lifecycleDispatcher.onServicePreSuperOnCreate()
    super.onCreate()
    AndroidInjection.inject(this)
    lifecycleDispatcher.lifecycle.addObserver(GenericLifecycleObserver { _, event ->
      Timber.d("event: $event")
    })
  }

  override fun onHoverMenuLaunched(intent: Intent, hoverView: HoverView) {
    val menu = LauncherMenu(launcherViewModel, this, this, { hoverView.collapse() })
    hoverView.setMenu(menu)
    hoverView.collapse()
  }

  override fun onBind(intent: Intent?): IBinder? {
    lifecycleDispatcher.onServicePreSuperOnBind()
    return super.onBind(intent)
  }

  override fun onStart(intent: Intent?, startId: Int) {
    lifecycleDispatcher.onServicePreSuperOnStart()
    super.onStart(intent, startId)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    lifecycleDispatcher.onServicePreSuperOnDestroy()
    super.onDestroy()
  }

  override fun getForegroundNotification(): Notification? {
    val builder = NotificationCompat.Builder(this, "launcher-foreground")
        .setContentText("Hover Launcher is running")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel("launcher-foreground",
          "Launcher Foreground Notification",
          NotificationManager.IMPORTANCE_MIN)
      builder.setChannelId(channel.id)
      getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
    return builder.build()
  }

  override fun getLifecycle(): Lifecycle = lifecycleDispatcher.lifecycle
}