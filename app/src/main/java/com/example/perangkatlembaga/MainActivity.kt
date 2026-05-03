package com.example.perangkatlembaga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.perangkatlembaga.databinding.ActivityMainBinding
import com.example.perangkatlembaga.Home.pertemuan2.SecondActivity
import com.example.perangkatlembaga.Home.pertemuan3.ThirdActivity
import com.example.perangkatlembaga.Home.pertemuan4.FourthActivity
import com.example.perangkatlembaga.Home.pertemuan7.SevenActivity
import com.example.perangkatlembaga.pertemuan_5.FifthActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar
        setSupportActionBar(binding.toolbar)
        
        // Setup Navigation Drawer Toggle
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Pertemuan 2 -> SecondActivity
        binding.btnPertemuan2.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        // Pertemuan 3 -> ThirdActivity
        binding.btnPertemuan3.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

        // Pertemuan 4 -> FourthActivity
        binding.btnPertemuan4.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("name", "Politeknik Caltex Riau")
            intent.putExtra("from", "Rumbai")
            intent.putExtra("age", 25)
            startActivity(intent)
        }

        // Pertemuan 5 -> FifthActivity
        binding.btnPertemuan5.setOnClickListener {
            startActivity(Intent(this, FifthActivity::class.java))
        }

        // Pertemuan 7 -> SevenActivity (New Package)
        binding.btnPertemuan7.setOnClickListener {
            startActivity(Intent(this, SevenActivity::class.java))
        }

        // Logout Button
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("isLogin", false)
                        apply()
                    }
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
}