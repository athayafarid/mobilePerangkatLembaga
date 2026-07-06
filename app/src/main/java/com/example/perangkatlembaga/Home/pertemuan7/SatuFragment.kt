package com.example.perangkatlembaga.Home.pertemuan7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.perangkatlembaga.R

class SatuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_satu, container, false)
        
        val lvVisiMisi = view.findViewById<ListView>(R.id.lvVisiMisi)

        // Data list Visi & Misi
        val listData = listOf(
            VisiMisiModel(
                "VISI DESA DIGITAL", 
                "Terwujudnya pelayanan masyarakat desa yang transparan, akuntabel, inovatif, dan mandiri berbasis teknologi informasi.",
                R.drawable.ic_info
            ),
            VisiMisiModel(
                "MISI DESA DIGITAL 1", 
                "Mengembangkan administrasi desa yang cepat, akurat, dan ramah lewat sistem digital.",
                R.drawable.ic_message
            ),
            VisiMisiModel(
                "MISI DESA DIGITAL 2", 
                "Meningkatkan transparansi program pembangunan desa kepada seluruh warga.",
                R.drawable.ic_more
            ),
            VisiMisiModel(
                "MISI DESA DIGITAL 3", 
                "Mempercepat penyampaian berita dan informasi penting secara real-time.",
                R.drawable.ic_notification
            )
        )

        // Menerapkan ListView dengan CustomAdapter
        val adapter = VisiMisiAdapter(requireContext(), listData)
        lvVisiMisi.adapter = adapter

        // Menangani klik item di ListView
        lvVisiMisi.setOnItemClickListener { _, _, position, _ ->
            val clickedItem = listData[position]
            Toast.makeText(requireContext(), "Membuka detail: ${clickedItem.title}", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}