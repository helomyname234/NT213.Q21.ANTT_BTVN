package com.example.secretnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var storage: NoteStorage
    private lateinit var tvNotes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = NoteStorage(this)

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etContent = findViewById<EditText>(R.id.etContent)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        tvNotes = findViewById(R.id.tvNotes)

        refreshList()

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val content = etContent.text.toString()
            if (title.isNotEmpty() && content.isNotEmpty()) {
                storage.addNote(title, content)
                etTitle.text.clear()
                etContent.text.clear()
                refreshList()
                sendAuditLog(title)
                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshList() {
        val notes = storage.getNotes()
        val text = notes.joinToString("\n\n") { "Title: ${it.title}\nContent: ${it.content}" }
        tvNotes.text = if (text.isEmpty()) "No notes" else text
    }

    private fun sendAuditLog(title: String) {
        val intent = Intent("com.example.auditlogger.action.WRITE_LOG")
        // Gửi đích danh tới package auditlogger
        intent.setPackage("com.example.auditlogger")
        intent.putExtra("message", "New note created: $title")

        // Gửi broadcast (Receiver đã được bảo vệ bởi permission trong Manifest của nó)
        sendBroadcast(intent)
    }
}