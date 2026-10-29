package com.example.notasapp.ui.notes

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notasapp.R
import com.example.notasapp.model.NoteListState

class NoteListActivity : AppCompatActivity() {

    private val viewModel: NoteListViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val rvNotes = findViewById<RecyclerView>(R.id.rvNotes)
        val pbList = findViewById<ProgressBar>(R.id.pbNoteList)

        adapter = NoteAdapter()
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapter

        viewModel.listState.observe(this) { state ->
            when (state) {
                is NoteListState.Loading -> pbList.visibility = View.VISIBLE
                is NoteListState.Success -> {
                    pbList.visibility = View.GONE
                    adapter.submitList(state.notes)
                }
                is NoteListState.Error -> {
                    pbList.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.cargarNotas()
    }
}