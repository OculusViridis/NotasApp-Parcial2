package com.example.notasapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.data.remote.TokenManager
import com.example.notasapp.ui.notes.CreateNoteActivity
import com.example.notasapp.ui.notes.NoteListActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnGoToCreate = findViewById<Button>(R.id.btnGoToCreate)
        val btnGoToList = findViewById<Button>(R.id.btnGoToList)

        val nombre = intent.getStringExtra("NOMBRE_USUARIO") ?: TokenManager.getUserName()
        tvWelcome.text = "¡Bienvenido(a), $nombre!"

        btnGoToCreate.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
        }

        btnGoToList.setOnClickListener {
            startActivity(Intent(this, NoteListActivity::class.java))
        }
    }
}