package com.example.notasapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notasapp.data.AuthRepository
import com.example.notasapp.data.remote.TokenManager
import com.example.notasapp.model.LoginState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _loginState.value = LoginState.Error("Por favor, ingresa correo y contraseña")
            return
        }

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = repository.login(email, pass)
                if (response.isSuccessful && response.body()?.exito == true) {
                    val body = response.body()!!
                    TokenManager.saveSession(body.token ?: "", body.nombre ?: "Usuario")
                    _loginState.value = LoginState.Success(body.nombre ?: "Usuario")
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de conexión al servidor: ${e.localizedMessage}")
            }
        }
    }
}