package com.rgds.chargingmonitor

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rgds.chargingmonitor.ui.theme.ChargingMonitorTheme

class MainActivity : ComponentActivity() {
    // Создаем переменную состояния для хранения текста
    private var chargingText by mutableStateOf("Hello, Android")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Регистрируем BroadcastReceiver
        val chargingReceiver = ChargingReceiver()
        chargingReceiver.onTextUpdate = { newText ->
            chargingText = newText // Обновляем текст при вызове колбэка
        }
        registerReceiver(chargingReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        setContent {
            ChargingMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ChargingText(chargingText)
                }
            }
        }

    }
}

@Composable
fun ChargingText(text: String) {
    Text(text)
}