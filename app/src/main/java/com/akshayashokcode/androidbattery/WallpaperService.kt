package com.akshayashokcode.androidbattery

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat

private const val CHANNEL_ID = "BatteryWallpaperService"
class WallpaperService : Service() {

    private lateinit var batteryReceiver: BatteryReceiver

    override fun onCreate() {
        super.onCreate()
        val notification = createNotification()

        startForeground(1, notification)

        batteryReceiver = BatteryReceiver()
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification() : Notification {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Battery Wallpaper Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)

        // Create intent to launch the app
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Battery Wallpaper Active")
            .setContentText("Updating wallpaper based on battery status")
            .setSmallIcon(R.drawable.twotone_battery_charging_50_24)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

}