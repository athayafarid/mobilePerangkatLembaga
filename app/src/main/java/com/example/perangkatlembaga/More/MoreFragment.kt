package com.example.perangkatlembaga.More

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.perangkatlembaga.databinding.FragmentMoreBinding

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

        // Data untuk ListView sederhana
        val settingsItems = arrayOf(
            "Pusat Bantuan",
            "Kebijakan Privasi",
            "Syarat dan Ketentuan",
            "Tentang Aplikasi",
            "Versi Aplikasi v1.0.0"
        )

        // Menggunakan ArrayAdapter untuk menampilkan list
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            settingsItems
        )

        binding.lvSettings.adapter = adapter

        // Memberikan aksi saat list diklik
        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            val item = settingsItems[position]
            Toast.makeText(requireContext(), "Membuka: $item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}