package com.akshayashokcode.androidbattery

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.akshayashokcode.androidbattery.ui.BatteryScreen
import com.akshayashokcode.androidbattery.viewmodel.BatteryViewModel
import com.akshayashokcode.androidbattery.viewmodel.ServiceViewModel

class MainActivity: ComponentActivity() {
    private val batteryViewModel: BatteryViewModel by viewModels()
    private val serviceViewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatteryScreen(batteryViewModel, serviceViewModel)
            RequestNotificationPermissionIfNeeded()
        }
    }

    @Composable
    fun RequestNotificationPermissionIfNeeded() {
        val context = LocalContext.current
        val activity = context as? Activity

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                val hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission && activity != null) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        1001 // your request code
                    )
                }
            }
        }
    }
}