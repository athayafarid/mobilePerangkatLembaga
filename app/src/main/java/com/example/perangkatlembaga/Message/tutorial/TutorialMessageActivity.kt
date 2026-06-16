package com.example.perangkatlembaga.Message.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.perangkatlembaga.LoginActivity
import com.example.perangkatlembaga.databinding.ActivityTutorialMessageBinding
import com.google.android.material.tabs.TabLayoutMediator

class TutorialMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment()
        )

        val adapter = TutorialFragmentAdapter(this, fragments)
        binding.tutorialMessageViewPager.adapter = adapter

        // Indikator Titik
        TabLayoutMediator(binding.tabLayoutIndicator, binding.tutorialMessageViewPager) { _, _ -> }.attach()

        // Perubahan Teks Tombol di Page Terakhir
        binding.tutorialMessageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == fragments.size - 1) {
                    binding.btnNext.text = "Ayo Mulai"
                } else {
                    binding.btnNext.text = "Lanjut"
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.tutorialMessageViewPager.currentItem + 1 < fragments.size) {
                binding.tutorialMessageViewPager.currentItem += 1
            } else {
                // Simpan status Onboarding selesai
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                sharedPref.edit().putBoolean("onboardingFinished", true).apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}