package ru.itis.mvp_firebase

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private var list: MutableList<Note> = emptyList<Note>().toMutableList()
) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(data: MutableList<Note>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
