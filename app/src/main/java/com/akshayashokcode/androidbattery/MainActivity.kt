package com.akshayashokcode.androidbattery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.akshayashokcode.androidbattery.ui.BatteryScreen
import com.akshayashokcode.androidbattery.ui.BatteryViewModel

class MainActivity: ComponentActivity() {
    private val batteryViewModel = BatteryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatteryScreen(batteryViewModel)
        }
    }
}