package com.example.perangkatlembaga.Home.pertemuan7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.perangkatlembaga.R

class DuaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dua, container, false)
        
        val lvTugas = view.findViewById<ListView>(R.id.lvTugas)

        // Data Terstruktur Tugas & Fungsi Perangkat Lembaga Desa
        val listTugas = listOf(
            TugasItem(
                "1",
                "Penyelenggaraan Administrasi",
                "Menyelenggarakan administrasi pemerintahan desa secara tertib, rapi, dan sesuai regulasi perundang-undangan."
            ),
            TugasItem(
                "2",
                "Pengelolaan Keuangan",
                "Mengelola anggaran dan keuangan desa secara akuntabel, transparan, serta bertanggung jawab penuh."
            ),
            TugasItem(
                "3",
                "Pelayanan Kependudukan",
                "Memberikan pelayanan prima kepada masyarakat dalam pengurusan surat keterangan, KK, KTP, dan akta lahir."
            ),
            TugasItem(
                "4",
                "Pembangunan Infrastruktur",
                "Melakukan koordinasi pembangunan fisik, pemeliharaan fasilitas umum, dan pengembangan sarana prasarana desa."
            ),
            TugasItem(
                "5",
                "Pembinaan Kemasyarakatan",
                "Melakukan pembinaan ketertiban, ketenteraman warga, pelestarian budaya desa, serta menggerakkan gotong royong."
            ),
            TugasItem(
                "6",
                "Pelaporan & Pertanggungjawaban",
                "Menyusun laporan kinerja dan pertanggungjawaban berkala mengenai realisasi seluruh program kerja desa."
            )
        )

        // Menerapkan Custom ArrayAdapter (TugasAdapter) untuk visualisasi yang menarik
        val adapter = TugasAdapter(requireContext(), listTugas)
        lvTugas.adapter = adapter

        // Menangani klik item di ListView
        lvTugas.setOnItemClickListener { _, _, position, _ ->
            val clickedItem = listTugas[position]
            Toast.makeText(
                requireContext(),
                "Fungsi:\n${clickedItem.desc}",
                Toast.LENGTH_LONG
            ).show()
        }

        return view
    }
}