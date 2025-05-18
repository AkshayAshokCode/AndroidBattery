# 🔋 Battery Mood Wallpaper (Android App)

An Android app that updates your device's **wallpaper in real-time** based on **battery level** and **charging status** — using a **foreground service** that keeps working even when the app is closed or killed.

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

<img width="400" alt="Screenshot 2025-05-18 at 8 21 45 PM" src="https://github.com/user-attachments/assets/2af4d001-b06a-43c9-84e5-293240e6805e" />
<img width="400" alt="Screenshot 2025-05-18 at 8 22 40 PM" src="https://github.com/user-attachments/assets/5c099373-48d0-4dac-8f9c-58fd5d07dfe4" />
<img width="400" alt="Screenshot 2025-05-18 at 8 22 13 PM" src="https://github.com/user-attachments/assets/2ec979ee-8fa0-41a6-a12f-3020e4b70b91" />
<img width="400" alt="Screenshot 2025-05-18 at 8 22 25 PM" src="https://github.com/user-attachments/assets/1b3c637f-cbaa-41a9-9a0c-a95dfaece515" />

---

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- ViewModel + StateFlow
- BatteryManager API
- Foreground Services
- WallpaperManager
- Runtime permission handling (Android 13+)
