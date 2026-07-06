package com.example.perangkatlembaga.Home.pertemuan7

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.perangkatlembaga.R
import com.example.perangkatlembaga.databinding.ActivitySevenBinding
import com.google.android.material.tabs.TabLayoutMediator

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

        // Setup ViewPager2 dengan FragmentStateAdapter
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // Setup TabLayoutMediator untuk menyelaraskan TabLayout + ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Visi Misi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_info)
                }
                1 -> {
                    tab.text = "Tugas & Fungsi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_more)
                }
                2 -> {
                    tab.text = "Kontak Layanan"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_message)
                    // Menerapkan Kustomisasi Tab Badge sesuai kriteria checklist
                    val badge = tab.orCreateBadge
                    badge.number = 5
                    badge.backgroundColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
                }
            }
        }.attach()
    }

    // Adapter ViewPager2 menggunakan FragmentStateAdapter
    private inner class ScreenSlidePagerAdapter(activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SatuFragment()
                1 -> DuaFragment()
                2 -> TigaFragment()
                else -> SatuFragment()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}