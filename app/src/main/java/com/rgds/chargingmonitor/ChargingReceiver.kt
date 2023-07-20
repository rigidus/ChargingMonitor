package com.rgds.chargingmonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log

class ChargingReceiver : BroadcastReceiver() {
    var onTextUpdate: ((String) -> Unit)? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

            val logText = if (isCharging) {
                "Device is charging!"
            } else {
                "Device is not charging"
            }
            Log.d("ChargingReceiver", logText)

            val newText = if (isCharging) {
                context?.getString(R.string.charging_text) ?: "Device is charging!"
            } else {
                context?.getString(R.string.not_charging_text) ?: "Device is not charging"
            }

            // Вызываем колбэк, чтобы передать новый текст в Activity
            onTextUpdate?.invoke(newText)

            // Выполните здесь дополнительные действия при подключении или отключении зарядки
            // Например, отправьте HTTP-запрос или сообщение в мессенджер
        }
    }
}

