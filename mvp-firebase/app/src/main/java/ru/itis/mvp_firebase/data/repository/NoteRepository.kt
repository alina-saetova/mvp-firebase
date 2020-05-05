package ru.itis.mvp_firebase.data.repository

import com.google.firebase.database.ValueEventListener

interface NoteRepository {

    suspend fun addNote(title: String, content: String)
    fun addListener(listener: ValueEventListener)
}
