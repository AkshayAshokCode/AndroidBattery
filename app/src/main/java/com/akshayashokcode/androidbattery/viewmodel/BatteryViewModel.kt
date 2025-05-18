package com.akshayashokcode.androidbattery.viewmodel

import android.app.Application
import android.content.*
import android.os.BatteryManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshayashokcode.androidbattery.WallpaperService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BatteryViewModel(application: Application) : AndroidViewModel(application) {

   // private val context = application.applicationContext

    private val _batteryPercent = MutableStateFlow(0)
    val batteryPercent: StateFlow<Int> get() = _batteryPercent

    private val _batteryStatusText = MutableStateFlow("Unknown ❓")
    val batteryStatusText: StateFlow<String> get() = _batteryStatusText

    private var debounceJob: Job? = null

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

            // Update UI states immediately
            _batteryPercent.value = percent
            _batteryStatusText.value = statusText

            // Debounce the service trigger
            debounceJob?.cancel()
            debounceJob = viewModelScope.launch {
                delay(3000L)  // wait 3 seconds of inactivity
                triggerWallpaperService()
            }
        }
    }

    init {
        val context = getApplication<Application>().applicationContext
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)
    }

    private fun triggerWallpaperService() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, WallpaperService::class.java)
        context.startForegroundService(intent)
    }

    override fun onCleared() {
        val context = getApplication<Application>().applicationContext
        super.onCleared()
        context.unregisterReceiver(batteryReceiver)
        debounceJob?.cancel()
    }
}