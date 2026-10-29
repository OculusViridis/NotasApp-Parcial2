package com.example.notasapp.data

import com.example.notasapp.data.remote.RetrofitClient
import com.example.notasapp.model.request.LoginRequest
import com.example.notasapp.model.request.NoteRequest

class AuthRepository {
    private val api = RetrofitClient.apiService
    suspend fun login(email: String, pass: String) = api.login(LoginRequest(email, pass))
}

class NoteRepository {
    private val api = RetrofitClient.apiService
    suspend fun crearNota(titulo: String, contenido: String) = api.crearNota(NoteRequest(titulo, contenido))
    suspend fun obtenerNotas() = api.obtenerNotas()
}