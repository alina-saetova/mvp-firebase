package ru.itis.mvp_firebase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.itis.mvp_firebase.data.Note
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private var database: FirebaseDatabase,
    private var firebaseAuth: FirebaseAuth
) : NoteRepository {

    override suspend fun addNote(title: String, content: String) {
        firebaseAuth.currentUser?.uid?.let {
            val note = Note(
                UUID.randomUUID().toString(),
                it,
                title,
                content
            )
            database.getReference("notes").child(it).child(note.id).setValue(note)
        }
    }

    override fun addListener(listener: ValueEventListener) {
        firebaseAuth.currentUser?.uid?.let {
            database.getReference("notes").child(it).addValueEventListener(listener)
        }
    }
}
