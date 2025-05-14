package com.akshayashokcode.androidbattery.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshayashokcode.androidbattery.BatteryInfo
import com.akshayashokcode.androidbattery.BatteryReceiver
import kotlinx.coroutines.launch

class BatteryViewModel : ViewModel() {
    val batteryInfo = mutableStateOf(BatteryInfo(0f, 0, 0.0, "Unknown"))

    fun registerBatteryReceiver(context: Context) {
        viewModelScope.launch {
            val receiver = BatteryReceiver(batteryInfo)
            val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            context.registerReceiver(receiver, filter)
        }
    }
}