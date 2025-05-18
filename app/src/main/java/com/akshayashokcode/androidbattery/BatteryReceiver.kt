package com.akshayashokcode.androidbattery

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.createBitmap

class BatteryReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

        val (color, emoji) = getColorAndEmoji(level, isCharging)
        setSolidColorWallpaperWithEmoji(context, color, emoji)
    }
}

fun setSolidColorWallpaperWithEmoji(context: Context, color: Int, emoji: String) {
    val width = Resources.getSystem().displayMetrics.widthPixels
    val height = Resources.getSystem().displayMetrics.heightPixels

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)

    // Fill background with color
    canvas.drawColor(color)

    // Set up paint for emoji text
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = (width.coerceAtMost(height)) / 2f  // Adjust text size
        isAntiAlias = true
    }

    // Draw emoji in center
    canvas.drawText(
        emoji,
        width / 2f,
        height / 2f - (paint.descent() + paint.ascent()) / 2,  // Vertically center
        paint
    )

    // Set the wallpaper
    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.setBitmap(bitmap)
}

fun getColorAndEmoji(level: Int, isCharging: Boolean): Pair<Int, String> {
    return when (level) {
        in 0..10 -> Pair(Color.parseColor("#8B0000"), "🪫")       // Dark Red
        in 11..30 -> Pair(Color.RED, "🪫")
        in 31..50 -> Pair(Color.parseColor("#FFA500"), "🔌")      // Orange
        in 51..70 -> Pair(Color.YELLOW, "⚡")
        in 71..90 -> Pair(Color.parseColor("#90EE90"), "🔌")      // Light Green
        in 91..100 -> Pair(Color.GREEN, if (isCharging) "⚡✅" else "✅")
        else -> Pair(Color.GRAY, "❓")
    }
}