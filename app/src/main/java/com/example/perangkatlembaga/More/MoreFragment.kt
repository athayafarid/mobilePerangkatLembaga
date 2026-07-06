package com.example.perangkatlembaga.More

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.perangkatlembaga.AboutFragment
import com.example.perangkatlembaga.NoteFragment
import com.example.perangkatlembaga.R
import com.example.perangkatlembaga.data.api.CatFactApiClient
import com.example.perangkatlembaga.databinding.FragmentMoreBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Task 4: Implementasi klik tombol Fakta Kucing (Retrofit)
        binding.btnCatFact.setOnClickListener {
            fetchCatFact()
        }

        // Membuka AboutFragment saat tombol diklik
        binding.btnAboutApp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AboutFragment())
                .addToBackStack(null)
                .commit()
        }

        // Membuka NoteFragment saat tombol diklik
        binding.btnNotes.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NoteFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun fetchCatFact() {
        // Menggunakan lifecycleScope untuk menjalankan Coroutines (Task 1)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Memanggil API melalui ApiClient (Task 3)
                val response = CatFactApiClient.apiService.getCatFact()
                
                // Menampilkan hasil (CatFactModel - Task 2) dalam Dialog
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Fakta Kucing Hari Ini")
                    .setMessage(response.fact)
                    .setPositiveButton("Tutup", null)
                    .show()
                    
            } catch (e: Exception) {
                // Menangani error jika gagal (misal: koneksi internet)
                Toast.makeText(requireContext(), "Gagal memuat data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}