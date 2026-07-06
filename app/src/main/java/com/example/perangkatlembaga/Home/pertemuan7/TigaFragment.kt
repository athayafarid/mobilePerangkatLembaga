package com.example.perangkatlembaga.Home.pertemuan7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.perangkatlembaga.R

class TigaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tiga, container, false)
        
        val lvKontak = view.findViewById<ListView>(R.id.lvKontak)

        // Data Kontak Layanan & Darurat Desa menggunakan List of Map
        val kontakList = listOf(
            mapOf("name" to "Kantor Kepala Desa", "number" to "(021) 12345678"),
            mapOf("name" to "Pelayanan Umum & Kependudukan", "number" to "0812-3456-7890"),
            mapOf("name" to "Ambulans Siaga Desa", "number" to "118 / (021) 87654321"),
            mapOf("name" to "Polsek Terdekat", "number" to "110"),
            mapOf("name" to "Layanan Pengaduan Warga", "number" to "info@desadigital.go.id")
        )

        // Menerapkan Custom KontakAdapter untuk tampilan kustom premium
        val adapter = KontakAdapter(requireContext(), kontakList)
        lvKontak.adapter = adapter

        // Menangani klik item di ListView
        lvKontak.setOnItemClickListener { _, _, position, _ ->
            val clickedItem = kontakList[position]
            val name = clickedItem["name"]
            val number = clickedItem["number"]
            Toast.makeText(requireContext(), "Menghubungi: $name\nNomor: $number", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}