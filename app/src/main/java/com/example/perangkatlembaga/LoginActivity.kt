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

        binding.tvTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.tvTogglePassword.text = "Sembunyikan"
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.tvTogglePassword.text = "Tampilkan"
            }
            // Perbaikan null safety
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email == "admin@gmail.com" && password == "admin123") {
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLogin", true)
                    apply()
                }

                // Ubah MainActivity menjadi BaseActivity agar masuk ke halaman dengan Bottom Navigation
                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}