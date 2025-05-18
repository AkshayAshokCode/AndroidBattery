package com.akshayashokcode.androidbattery

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.core.graphics.createBitmap

class BatteryReceiver(private val context: Context) : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level * 100 / scale.toFloat()

        val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) // in mV
        val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.0 // in °C
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

        val wallpaperManager = WallpaperManager.getInstance(context)

        val imageRes = when {
            isCharging && level >= 90 -> R.drawable.wallpaper
            level <= 15 -> R.drawable.wallpaper
            else -> R.drawable.wallpaper
        }
        val color = when {
            isCharging && level >= 90 -> Color.GREEN
            level <= 15 -> Color.RED
            else -> Color.YELLOW
        }

       // wallpaperManager.clearWallpaper()
      //  val bitmap = BitmapFactory.decodeResource(context.resources, imageRes)
      // wallpaperManager.setBitmap(bitmap)
        setSolidColorWallpaper(context, color)
    }
}

fun setSolidColorWallpaper(context: Context, color: Int) {
    val width = Resources.getSystem().displayMetrics.widthPixels
    val height = Resources.getSystem().displayMetrics.heightPixels

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color) // <- Fill with your chosen color

    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.setBitmap(bitmap)
}

data class BatteryInfo(
    val batteryPercentage: Float,
    val voltage: Int,
    val temperature: Double,
    val status: String
)