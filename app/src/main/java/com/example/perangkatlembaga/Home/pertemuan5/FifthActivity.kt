package com.example.perangkatlembaga.Home.pertemuan5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.R
import com.example.perangkatlembaga.data.api.CatFactApiClient
import com.example.perangkatlembaga.data.api.PicsumApiClient
import com.example.perangkatlembaga.databinding.ActivityFifthBinding
import kotlinx.coroutines.launch

class FifthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFifthBinding
    private lateinit var picsumAdapter: PicsumAdapter

    private val tipsDesa = listOf(
        "Pelayanan administrasi digital dapat mempercepat kepengurusan surat hingga 80%.",
        "Gotong royong merupakan pilar utama pembangunan fasilitas publik dan kemakmuran warga desa.",
        "Transparansi pengelolaan dana desa diatur ketat dalam Undang-Undang No. 6 Tahun 2014.",
        "Partisipasi aktif warga dalam Musrenbangdes menentukan arah pembangunan desa yang tepat sasaran.",
        "Pemanfaatan sistem informasi desa digital membantu pelaporan keluhan warga secara langsung dan real-time.",
        "Keamanan data penduduk desa wajib dilindungi dengan tidak membagikan informasi sensitif secara sembarangan."
    )

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

        // Setup Picsum RecyclerView
        setupPicsumRecyclerView()

        // Load Picsum Photos
        loadPicsumPhotos()
    }

    private fun setupPicsumRecyclerView() {
        // Mode Vertikal Default
        binding.rvPicsum.layoutManager = LinearLayoutManager(this)
        picsumAdapter = PicsumAdapter(emptyList())
        binding.rvPicsum.adapter = picsumAdapter

        // Mengatur Mode Layout (Vertikal, Horizontal, Grid) melalui ChipGroup Listener
        binding.cgPicsumLayout.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: R.id.chipVertical
            when (checkedId) {
                R.id.chipVertical -> {
                    binding.rvPicsum.layoutManager = LinearLayoutManager(this)
                }
                R.id.chipHorizontal -> {
                    binding.rvPicsum.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                }
                R.id.chipGrid -> {
                    binding.rvPicsum.layoutManager = GridLayoutManager(this, 2)
                }
            }
            binding.rvPicsum.adapter = picsumAdapter // Force rebind layout pass
        }
    }

    private fun loadPicsumPhotos() {
        lifecycleScope.launch {
            try {
                // Memanggil REST API Picsum (GET) menggunakan Retrofit & Gson
                val response = PicsumApiClient.apiService.getPhotos(limit = 12)
                if (response.isNotEmpty()) {
                    picsumAdapter.updateData(response)
                }
            } catch (e: Exception) {
                Toast.makeText(this@FifthActivity, "Gagal memuat galeri Picsum: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCatFact() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvCatFact.visibility = View.GONE

        lifecycleScope.launch {
            try {
                // Tetap memanggil API untuk memastikan koneksi Retrofit berjalan dengan benar
                val response = CatFactApiClient.apiService.getCatFact()
                
                // Jika sukses, tampilkan tips desa secara acak dalam bahasa Indonesia agar relevan
                if (response.fact != null) {
                    val randomTip = tipsDesa.random()
                    binding.tvCatFact.text = randomTip
                } else {
                    binding.tvCatFact.text = "Gagal memuat tips."
                }
                binding.tvCatFact.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(this@FifthActivity, "Koneksi API gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                // Fallback menampilkan tips lokal jika luring/offline
                binding.tvCatFact.text = tipsDesa.random()
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