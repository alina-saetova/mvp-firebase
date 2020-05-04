package ru.itis.mvp_firebase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_view.view.*
import ru.itis.mvp_firebase.databinding.FragmentUserDataBinding
import java.util.*

class UserDataFragment : Fragment() {

    private lateinit var binding: FragmentUserDataBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var user: FirebaseUser

    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDataBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fab.setOnClickListener(addNote)

        adapter = NoteAdapter()
        binding.recyclerView.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        database = Firebase.database.reference.child(TABLE_NAME).child(user.uid)
        database.addValueEventListener(postListener)

        return binding.root
    }

    private val addNote = View.OnClickListener {
        val view = layoutInflater.inflate(R.layout.dialog_view, null)
        MaterialAlertDialogBuilder(context)
            .setTitle(DIALOG_TITLE)
            .setView(view)
            .setNegativeButton(DIALOG_CANCEL) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(DIALOG_SAVE) { dialog, _ ->
                val title = view.findViewById<EditText>(R.id.et_title).text
                val content = view.findViewById<EditText>(R.id.et_content).text

                if (title.isNullOrEmpty() || content.isNullOrEmpty()) {
                    view.et_title.error = ERROR_MSG
                    view.et_content.error = ERROR_MSG
                } else {
                    val note = Note(UUID.randomUUID().toString(), user.uid, title.toString(), content.toString())
                    database.child(note.id).setValue(note)
                }
                dialog.dismiss()
            }
            .show()
    }

    private val clickListener = {id: String ->
        Toast.makeText(activity, id, Toast.LENGTH_LONG).show()
    }

    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list = mutableListOf<Note>()
            dataSnapshot.children.forEach {
                val note = it.getValue(Note::class.java)
                note?.let { it1 -> list.add(it1) }
            }
            adapter.update(list)
        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    companion object {
        const val DIALOG_TITLE = "Add a note"
        const val DIALOG_CANCEL = "cancel"
        const val DIALOG_SAVE = "save"
        const val TABLE_NAME = "notes"
        const val ERROR_MSG = "Required"
        const val TAG = "TAG"
    }
}
