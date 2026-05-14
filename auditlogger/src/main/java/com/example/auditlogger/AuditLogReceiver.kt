package com.example.auditlogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AuditLogReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AuditLogReceiver", "Action received: ${intent.action}")
        if (intent.action == "com.example.auditlogger.action.WRITE_LOG") {
            val message = intent.getStringExtra("message") ?: "Unknown log"
            val storage = LogStorage(context)
            storage.addLog(message)
            Log.d("AuditLogReceiver", "Log saved: $message")
        }
    }
}