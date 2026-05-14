package com.example.secretnotes

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

class SecretNoteProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?
    ): Cursor {
        // Tạo MatrixCursor với các cột yêu cầu
        val cursor = MatrixCursor(arrayOf("_id", "title", "content", "created_at"))

        context?.let {
            val storage = NoteStorage(it)
            val notes = storage.getNotes()

            // Đổ dữ liệu từ JSON list vào Cursor
            for (note in notes) {
                cursor.addRow(arrayOf(note.id, note.title, note.content, note.createdAt))
            }
        }
        return cursor
    }

    // Các hàm dưới đây không hỗ trợ
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
}