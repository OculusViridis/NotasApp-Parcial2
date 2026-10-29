package com.example.notasapp.model.response

data class LoginResponse(
    val exito: Boolean,
    val mensaje: String,
    val token: String?,
    val nombre: String?,
    val email: String?
)