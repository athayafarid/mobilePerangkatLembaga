package com.example.perangkatlembaga.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.Home.pertemuan2.SecondActivity
import com.example.perangkatlembaga.Home.pertemuan3.ThirdActivity
import com.example.perangkatlembaga.Home.pertemuan4.FourthActivity
import com.example.perangkatlembaga.LoginActivity
import com.example.perangkatlembaga.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Setup News RecyclerView
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        
        // Initial Fetch News
        loadNews()

        // Implementasi Klik Refresh untuk Berita
        binding.btnRefreshNews.setOnClickListener {
            loadNews()
        }

        // Navigasi Fitur
        setupNavigation(sharedPref)
    }

    private fun loadNews() {
        binding.pbNews.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                // Mengambil berita terbaru dari API
                val response = NewsApiClient.apiService.getNews()
                if (response.data != null) {
                    binding.rvNews.adapter = NewsAdapter(response.data)
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat berita", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat berita", Toast.LENGTH_SHORT).show()
            } finally {
                binding.pbNews.visibility = View.GONE
            }
        }
    }

    private fun setupNavigation(sharedPref: android.content.SharedPreferences) {
        binding.btnDataRT.setOnClickListener {
            startActivity(Intent(requireContext(), SecondActivity::class.java))
        }
        
        binding.btnDataRW.setOnClickListener {
            startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }
        
        binding.btnAnggota.setOnClickListener {
            val intent = Intent(requireContext(), FourthActivity::class.java).apply {
                putExtra("name", "Lembaga Desa")
                putExtra("from", "Kantor Desa")
                putExtra("age", 0)
            }
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sharedPref.edit().putBoolean("isLogin", false).apply()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
