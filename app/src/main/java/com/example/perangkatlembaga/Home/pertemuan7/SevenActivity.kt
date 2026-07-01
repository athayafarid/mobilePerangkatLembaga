package com.example.perangkatlembaga.Home.pertemuan7

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.perangkatlembaga.databinding.ActivitySevenBinding
import com.google.android.material.tabs.TabLayout

class SevenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySevenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySevenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Panduan & Visi Misi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Setup TabLayout Tabs
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Visi Misi"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tugas & Fungsi"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Kontak Layanan"))

        // Load Visi Misi Fragment by default (so it is not blank initially)
        if (savedInstanceState == null) {
            replaceFragment(SatuFragment())
        }

        // Handle Tab Selection
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> replaceFragment(SatuFragment())
                    1 -> replaceFragment(DuaFragment())
                    2 -> replaceFragment(TigaFragment())
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}