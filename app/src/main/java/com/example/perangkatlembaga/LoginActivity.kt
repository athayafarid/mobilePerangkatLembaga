package com.example.perangkatlembaga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perangkatlembaga.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnLogin.setOnClickListener {
            val usernameInput = binding.etEmail.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val registeredUser = sharedPref.getString("reg_username", "admin@gmail.com")
            val registeredPass = sharedPref.getString("reg_password", "admin123")

            if (usernameInput.isEmpty()) {
                binding.etEmail.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (passwordInput.isEmpty()) {
                binding.etPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            if (usernameInput == registeredUser && passwordInput == registeredPass) {
                with(sharedPref.edit()) {
                    putBoolean("isLogin", true)
                    apply()
                }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}