package com.example.notasapp.ui.notes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.model.CreateNoteState

class CreateNoteActivity : AppCompatActivity() {

    private val viewModel: CreateNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val etTitle = findViewById<EditText>(R.id.etNoteTitle)
        val etContent = findViewById<EditText>(R.id.etNoteContent)
        val btnSave = findViewById<Button>(R.id.btnSaveNote)
        val pbCreate = findViewById<ProgressBar>(R.id.pbCreateNote)

        btnSave.setOnClickListener {
            viewModel.crearNota(
                etTitle.text.toString().trim(),
                etContent.text.toString().trim()
            )
        }

        viewModel.createState.observe(this) { state ->
            when (state) {
                is CreateNoteState.Loading -> {
                    pbCreate.visibility = View.VISIBLE
                    btnSave.isEnabled = false
                }
                is CreateNoteState.Success -> {
                    pbCreate.visibility = View.GONE
                    btnSave.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is CreateNoteState.Error -> {
                    pbCreate.visibility = View.GONE
                    btnSave.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}