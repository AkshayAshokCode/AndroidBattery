package com.akshayashokcode.androidbattery.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.akshayashokcode.androidbattery.viewmodel.BatteryViewModel
import com.akshayashokcode.androidbattery.viewmodel.ServiceViewModel

@Composable
fun BatteryScreen(batteryViewModel: BatteryViewModel,  serviceViewModel: ServiceViewModel) {
    val batteryPercent by batteryViewModel.batteryPercent.collectAsState()
    val batteryStatusText by batteryViewModel.batteryStatusText.collectAsState()
    val isServiceRunning by serviceViewModel.isServiceRunning.collectAsState()

    BatteryWallpaperContent(
        batteryPercent = batteryPercent,
        batteryStatusText = batteryStatusText,
        isServiceRunning = isServiceRunning,
        onToggleService = {
            if (isServiceRunning) {
                serviceViewModel.stopWallpaperService()
            } else {
                serviceViewModel.startWallpaperService()
            }
        }
    )
}

@Composable
fun BatteryWallpaperContent(
    batteryPercent: Int,
    batteryStatusText: String,
    isServiceRunning: Boolean,
    onToggleService: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BatteryInfoSection(batteryPercent, batteryStatusText)
        Spacer(modifier = Modifier.height(24.dp))
        ServiceControlButton(isServiceRunning, onToggleService)
    }
}


@Composable
fun BatteryInfoSection(batteryPercent: Int, batteryStatusText: String) {
    Text("Battery Level: $batteryPercent%", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text("Status: $batteryStatusText", style = MaterialTheme.typography.bodyLarge)
}


@Composable
fun ServiceControlButton(
    isServiceRunning: Boolean,
    onToggleService: () -> Unit
) {
    val buttonColors = if (isServiceRunning) {
        ButtonDefaults.buttonColors(containerColor = Color.Red)
    } else {
        ButtonDefaults.buttonColors(containerColor = Color.Unspecified)
    }

    Button(
        onClick = onToggleService,
        colors = buttonColors
    ) {
        Text(
            if (isServiceRunning) "Stop Wallpaper Service" else "Start Wallpaper Service",
            color = Color.White
        )
    }
}
