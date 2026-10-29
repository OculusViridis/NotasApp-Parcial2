package com.example.notasapp.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notasapp.R
import com.example.notasapp.model.response.NoteResponse

class NoteAdapter(private var notes: List<NoteResponse> = emptyList()) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    fun submitList(newNotes: List<NoteResponse>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvItemContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.tvTitle.text = note.titulo
        holder.tvContent.text = note.contenido
    }

    override fun getItemCount(): Int = notes.size
}