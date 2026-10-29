package com.example.notasapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.data.remote.TokenManager
import com.example.notasapp.model.LoginState
import com.example.notasapp.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val pbLogin = findViewById<ProgressBar>(R.id.pbLogin)

        btnLogin.setOnClickListener {
            viewModel.login(
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }

        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    pbLogin.visibility = View.VISIBLE
                    btnLogin.isEnabled = false
                }
                is LoginState.Success -> {
                    pbLogin.visibility = View.GONE
                    btnLogin.isEnabled = true
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("NOMBRE_USUARIO", state.nombre)
                    startActivity(intent)
                    finish()
                }
                is LoginState.Error -> {
                    pbLogin.visibility = View.GONE
                    btnLogin.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}