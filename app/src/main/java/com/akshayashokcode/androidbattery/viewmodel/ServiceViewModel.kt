package com.akshayashokcode.androidbattery.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.akshayashokcode.androidbattery.WallpaperService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.edit

const val PREFS_NAME = "service_prefs"
const val KEY_SERVICE_RUNNING = "key_service_running"

class ServiceViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isServiceRunning = MutableStateFlow(false)
    val isServiceRunning: StateFlow<Boolean> get() = _isServiceRunning

    init {
        // Read saved state on init
        val running = prefs.getBoolean(KEY_SERVICE_RUNNING, false)
        _isServiceRunning.value = running
    }

    fun startWallpaperService() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, WallpaperService::class.java)
        context.startForegroundService(intent)
        _isServiceRunning.value = true
        prefs.edit { putBoolean(KEY_SERVICE_RUNNING, true) }
    }

    fun stopWallpaperService() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, WallpaperService::class.java)
        context.stopService(intent)
        _isServiceRunning.value = false
        prefs.edit { putBoolean(KEY_SERVICE_RUNNING, false) }
    }
}
