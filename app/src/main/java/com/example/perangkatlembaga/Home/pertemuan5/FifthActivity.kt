package com.example.perangkatlembaga.Home.pertemuan5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.perangkatlembaga.R
import com.example.perangkatlembaga.data.api.CatFactApiClient
import com.example.perangkatlembaga.databinding.ActivityFifthBinding
import kotlinx.coroutines.launch

class FifthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFifthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFifthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Portal Web & Layanan Desa"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.btnFetchFact.setOnClickListener {
            fetchCatFact()
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchCatFact() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvCatFact.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val response = CatFactApiClient.apiService.getCatFact()
                binding.tvCatFact.text = response.fact
                binding.tvCatFact.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(this@FifthActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.tvCatFact.text = "Gagal mengambil data."
                binding.tvCatFact.visibility = View.VISIBLE
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}