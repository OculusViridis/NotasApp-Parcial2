package com.example.notasapp.data.remote

import com.example.notasapp.model.request.LoginRequest
import com.example.notasapp.model.request.NoteRequest
import com.example.notasapp.model.response.LoginResponse
import com.example.notasapp.model.response.NoteResponse
import com.example.notasapp.model.response.StandardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/notas")
    suspend fun crearNota(@Body request: NoteRequest): Response<StandardResponse>

    @GET("api/notas")
    suspend fun obtenerNotas(): Response<List<NoteResponse>>
}