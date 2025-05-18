package com.akshayashokcode.androidbattery.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BatteryViewModel(application: Application) : AndroidViewModel(application) {

    private val _batteryPercent = MutableStateFlow(0)
    val batteryPercent: StateFlow<Int> get() = _batteryPercent

    private val _batteryStatusText = MutableStateFlow("Unknown ❓")
    val batteryStatusText: StateFlow<String> get() = _batteryStatusText

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: return
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
            val percent = (level * 100 / scale)
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val statusText = when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> "Charging ⚡"
                BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging 🔋"
                BatteryManager.BATTERY_STATUS_FULL -> "Full ✅"
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging ⛔"
                else -> "Unknown ❓"
            }

            // Update UI states
            _batteryPercent.value = percent
            _batteryStatusText.value = statusText
        }
    }

    init {
        val context = getApplication<Application>().applicationContext
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)
    }

    override fun onCleared() {
        val context = getApplication<Application>().applicationContext
        super.onCleared()
        context.unregisterReceiver(batteryReceiver)
    }
}