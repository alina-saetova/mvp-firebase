package ru.itis.mvp_firebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.data.Note
import ru.itis.mvp_firebase.databinding.ItemNoteBinding

class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) {
        binding.note = note
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) =
            NoteViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_note,
                    parent,
                    false
                )
            )
    }
}
