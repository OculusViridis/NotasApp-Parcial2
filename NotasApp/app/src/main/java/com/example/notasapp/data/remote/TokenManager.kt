package com.example.notasapp.data.remote

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "notas_prefs"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_USER_NAME = "user_name"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSession(token: String, nombre: String) {
        prefs.edit().putString(KEY_TOKEN, token).putString(KEY_USER_NAME, nombre).apply()
    }

    fun getToken(): String? = if (::prefs.isInitialized) prefs.getString(KEY_TOKEN, null) else null

    fun getUserName(): String = if (::prefs.isInitialized) prefs.getString(KEY_USER_NAME, "Usuario") ?: "Usuario" else "Usuario"
}