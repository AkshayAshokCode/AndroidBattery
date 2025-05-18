# 🔋 Battery Mood Wallpaper (Android App)

An Android app that updates your device's **wallpaper in real-time** based on **battery level** and **charging status** — using a **foreground service** that keeps working even when the app is closed or killed.

*(Add video or GIF demo here)*

---

## 📱 Features

- 🔋 **Live battery status detection**
- 🎨 **Dynamic wallpaper updates** based on:
  - Battery level
  - Charging/discharging state
- 🛡️ **Foreground service**: keeps running even if the app is killed
- 💡 Composable UI built with **Jetpack Compose**
- 🔔 **Notification** with clickable action to reopen the app
- 🟢 Service can be started/stopped with one button

---

## 🧠 Idea Behind

Inspired by the concept of "**Battery Mood**" — turning something invisible (your battery status) into a visual experience. A great way to explore **BatteryManager**, **foreground services**, and **wallpaper management** in Android.

---

## 📸 Screenshots

*(Add 1–2 screenshots or a screen recording GIF here)*

---

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- ViewModel + StateFlow
- BatteryManager API
- Foreground Services
- WallpaperManager
- Runtime permission handling (Android 13+)
