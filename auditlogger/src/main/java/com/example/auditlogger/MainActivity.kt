package com.example.auditlogger

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var logStorage: LogStorage
    private lateinit var tvSecretNotes: TextView
    private lateinit var tvAuditLogs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logStorage = LogStorage(this)
        tvSecretNotes = findViewById(R.id.tvSecretNotes)
        tvAuditLogs = findViewById(R.id.tvAuditLogs)
        val btnLoadNotes = findViewById<Button>(R.id.btnLoadNotes)
        val btnReloadLogs = findViewById<Button>(R.id.btnReloadLogs)

        btnLoadNotes.setOnClickListener {
            loadSecretNotes()
        }

        btnReloadLogs.setOnClickListener {
            loadAuditLogs()
        }

        loadAuditLogs()
    }

    @SuppressLint("Range")
    private fun loadSecretNotes() {
        // Đã sửa sang com.example
        val uri = Uri.parse("content://com.example.secretnotes.provider/notes")
        val stringBuilder = StringBuilder()

        try {
            val cursor = contentResolver.query(uri, null, null, null, null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val content = cursor.getString(cursor.getColumnIndex("content"))
                    stringBuilder.append("[$title] - $content\n")
                }
                cursor.close()
                tvSecretNotes.text = stringBuilder.toString()
            } else {
                tvSecretNotes.text = "No notes found or Provider empty"
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            tvSecretNotes.text = "Error: Permission denied (Check Signature/Permission)"
        } catch (e: Exception) {
            Toast.makeText(this, "Provider unavailable! Is Secret Notes installed?", Toast.LENGTH_LONG).show()
            tvSecretNotes.text = "Error: Provider unavailable"
        }
    }

    private fun loadAuditLogs() {
        val logs = logStorage.getLogs()
        val text = logs.joinToString("\n") { "• ${it.message}" }
        tvAuditLogs.text = if (text.isEmpty()) "No audit logs" else text
    }
}