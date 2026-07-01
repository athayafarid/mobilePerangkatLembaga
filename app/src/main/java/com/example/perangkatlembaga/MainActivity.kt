package com.example.perangkatlembaga

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.Home.NewsAdapter
import com.example.perangkatlembaga.Home.NewsApiClient
import com.example.perangkatlembaga.Home.pertemuan2.SecondActivity
import com.example.perangkatlembaga.Home.pertemuan3.ThirdActivity
import com.example.perangkatlembaga.Home.pertemuan4.FourthActivity
import com.example.perangkatlembaga.Home.pertemuan5.FifthActivity
import com.example.perangkatlembaga.Home.pertemuan7.SevenActivity
import com.example.perangkatlembaga.databinding.ActivityMainBinding
import com.example.perangkatlembaga.utils.NotificationHelper
import com.example.perangkatlembaga.utils.PermissionHelper
import com.example.perangkatlembaga.utils.ReminderReceiver
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    // Launcher untuk izin notifikasi (Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNotificationPermission()
        setupToolbar()
        setupDashboard()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupDashboard() {
        // --- News List ---
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        loadNews()
        binding.btnRefreshNews.setOnClickListener { loadNews() }

        // --- Reminder Feature ---
        binding.btnSetReminder.setOnClickListener {
            val minutesStr = binding.etReminderMinutes.text.toString()
            if (minutesStr.isNotEmpty()) {
                val minutes = minutesStr.toLong()
                setReminder(minutes)
                
                // Feedback notifikasi langsung
                val intent = Intent(this, MainActivity::class.java)
                NotificationHelper.showNotification(
                    this,
                    "Pengingat Disetel",
                    "Aplikasi akan mengingatkan agenda desa dalam $minutes menit.",
                    intent
                )
            } else {
                Toast.makeText(this, "Masukkan jumlah menit", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Navigation ---
        binding.btnDataRT.setOnClickListener { startActivity(Intent(this, SecondActivity::class.java)) }
        binding.btnDataRW.setOnClickListener { startActivity(Intent(this, ThirdActivity::class.java)) }
        binding.btnPertemuan4.setOnClickListener { startActivity(Intent(this, FourthActivity::class.java)) }
        binding.btnPertemuan5.setOnClickListener { startActivity(Intent(this, FifthActivity::class.java)) }
        binding.btnPertemuan7.setOnClickListener { startActivity(Intent(this, SevenActivity::class.java)) }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit().putBoolean("isLogin", false).apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun loadNews() {
        binding.pbNews.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = NewsApiClient.apiService.getNews()
                if (response.success == true) {
                    binding.rvNews.adapter = NewsAdapter(response.data ?: emptyList())
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Gagal memuat berita", Toast.LENGTH_SHORT).show()
            } finally {
                binding.pbNews.visibility = View.GONE
            }
        }
    }

    private fun setReminder(minutes: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java).apply {
            putExtra("title", "Agenda Perangkat Desa")
            putExtra("message", "Waktunya memeriksa laporan rutin atau data terbaru!")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 100, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val triggerTime = System.currentTimeMillis() + (minutes * 60 * 1000)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        Toast.makeText(this, "Pengingat aktif ($minutes menit lagi)", Toast.LENGTH_SHORT).show()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.homeContent.visibility = View.VISIBLE
                    binding.fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.navigation_about -> {
                    showFragment(AboutFragment())
                    true
                }
                R.id.navigation_profile -> {
                    showFragment(ProfileFragment())
                    true
                }
                R.id.navigation_more -> {
                    showFragment(com.example.perangkatlembaga.More.MoreFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        binding.homeContent.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun checkNotificationPermission() {
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(requestPermissionLauncher, Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}