package com.example.notasapp.model.response

data class NoteResponse(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val fecha_creacion: String?
)