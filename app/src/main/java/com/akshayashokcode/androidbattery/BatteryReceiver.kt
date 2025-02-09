package com.akshayashokcode.androidbattery

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.compose.runtime.MutableState

class BatteryReceiver(private val batteryInfo: MutableState<BatteryInfo>) : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level * 100 / scale.toFloat()

        val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) // in mV
        val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.0 // in °C
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

        val statusText = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging ⚡"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging 🔋"
            BatteryManager.BATTERY_STATUS_FULL -> "Full ✅"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging ⛔"
            else -> "Unknown ❓"
        }

        batteryInfo.value = (BatteryInfo(batteryPct, voltage, temperature, statusText))
    }
}

data class BatteryInfo(
    val batteryPercentage: Float,
    val voltage: Int,
    val temperature: Double,
    val status: String
)