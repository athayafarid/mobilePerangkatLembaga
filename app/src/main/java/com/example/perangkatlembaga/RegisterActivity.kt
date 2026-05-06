package com.example.perangkatlembaga

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perangkatlembaga.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatePicker()
        setupReligionDropdown()

        binding.btnRegister.setOnClickListener {
            performRegistration()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
        
        // Clear errors when gender is selected
        binding.rgGender.setOnCheckedChangeListener { _, _ ->
            binding.tvGenderError.visibility = View.GONE
        }
    }

    private fun setupDatePicker() {
        binding.etBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.etBirthDate.setText(date)
                    binding.tilBirthDate.error = null
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun setupReligionDropdown() {
        val religions = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Khonghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, religions)
        binding.actvReligion.setAdapter(adapter)
        binding.actvReligion.setOnItemClickListener { _, _, _, _ ->
            binding.tilReligion.error = null
        }
    }

    private fun performRegistration() {
        val name = binding.etName.text.toString().trim()
        val birthDate = binding.etBirthDate.text.toString().trim()
        val religion = binding.actvReligion.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        
        val selectedGenderId = binding.rgGender.checkedRadioButtonId
        val gender = if (selectedGenderId == R.id.rbMale) "Laki-laki" else if (selectedGenderId == R.id.rbFemale) "Perempuan" else ""

        var isValid = true

        // Reset errors
        binding.tilName.error = null
        binding.tilBirthDate.error = null
        binding.tilReligion.error = null
        binding.tilUsername.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
        binding.tvGenderError.visibility = View.GONE

        if (name.isEmpty()) {
            binding.tilName.error = "Nama tidak boleh kosong"
            isValid = false
        }
        
        if (birthDate.isEmpty()) {
            binding.tilBirthDate.error = "Tanggal lahir harus dipilih"
            isValid = false
        }
        
        if (gender.isEmpty()) {
            binding.tvGenderError.visibility = View.VISIBLE
            isValid = false
        }
        
        if (religion.isEmpty()) {
            binding.tilReligion.error = "Agama harus dipilih"
            isValid = false
        }
        
        if (username.isEmpty()) {
            binding.tilUsername.error = "Username tidak boleh kosong"
            isValid = false
        }
        
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        }
        
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Konfirmasi Password tidak boleh kosong"
            isValid = false
        } else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Password tidak cocok"
            isValid = false
        }

        if (isValid) {
            saveToSharedPrefs(name, birthDate, gender, religion, username, password)
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveToSharedPrefs(name: String, birthDate: String, gender: String, religion: String, username: String, pass: String) {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("reg_name", name)
            putString("reg_birthDate", birthDate)
            putString("reg_gender", gender)
            putString("reg_religion", religion)
            putString("reg_username", username)
            putString("reg_password", pass)
            apply()
        }
    }
}