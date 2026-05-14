package com.example.auditlogger

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class LogEntry(val message: String, val timestamp: Long)

class LogStorage(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("audit_logs_db", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getLogs(): List<LogEntry> {
        val json = prefs.getString("logs", "[]")
        val type = object : TypeToken<List<LogEntry>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun addLog(message: String) {
        val logs = getLogs().toMutableList()
        logs.add(LogEntry(message, System.currentTimeMillis()))
        prefs.edit().putString("logs", gson.toJson(logs)).apply()
    }
}