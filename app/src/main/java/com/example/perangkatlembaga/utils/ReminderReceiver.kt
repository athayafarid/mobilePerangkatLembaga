package com.example.perangkatlembaga.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.perangkatlembaga.MainActivity

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Agenda Perangkat Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya memeriksa laporan rutin atau data terbaru!"
        
        // Mengarahkan ke MainActivity saat notifikasi diklik
        val notificationIntent = Intent(context, MainActivity::class.java)
        NotificationHelper.showNotification(context, title, message, notificationIntent)
    }
}