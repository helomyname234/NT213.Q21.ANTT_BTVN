package com.example.secretnotes

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Note(val id: Long, val title: String, val content: String, val createdAt: Long)

class NoteStorage(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("secret_notes_db", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getNotes(): List<Note> {
        val json = prefs.getString("notes", "[]")
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun addNote(title: String, content: String): Note {
        val notes = getNotes().toMutableList()
        val newNote = Note(System.currentTimeMillis(), title, content, System.currentTimeMillis())
        notes.add(newNote)
        prefs.edit().putString("notes", gson.toJson(notes)).apply()
        return newNote
    }
}