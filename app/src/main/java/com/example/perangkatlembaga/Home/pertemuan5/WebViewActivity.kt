package com.example.perangkatlembaga.pertemuan_5

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
// Pastikan binding merujuk ke package perangkatlembaga
import com.example.perangkatlembaga.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    // Variabel penanda agar tidak lag saat scroll
    private var isAppBarVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi View Binding untuk perangkatlembaga project
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengaktifkan toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Web Merdeka"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://merdeka.com")

        // Agar Toolbar hide/show saat scroll web dengan optimasi anti-lag
        binding.webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && isAppBarVisible) {
                binding.appBar.setExpanded(false, true) // sembunyikan
                isAppBarVisible = false
            } else if (scrollY < oldScrollY && !isAppBarVisible) {
                binding.appBar.setExpanded(true, true) // tampilkan
                isAppBarVisible = true
            }
        }
    }

    // Mengaktifkan tombol back pada toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Mengontrol navigasi back pada WebView agar tidak langsung keluar aplikasi
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack() // Kembali ke halaman web sebelumnya
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed() // Keluar dari activity
        }
    }
}