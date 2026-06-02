package com.example.perangkatlembaga.Message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.R
import com.example.perangkatlembaga.databinding.FragmentMessageBinding
import com.google.android.material.tabs.TabLayout

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Data Dummy untuk Pesan
        val pesanList = listOf(
            MessageModel("Bapak Kades", "Undangan rapat koordinasi besok jam 9 pagi.", "08:00", R.drawable.ic_message),
            MessageModel("Ibu RW 02", "Data kependudukan baru sudah saya kirim via email.", "Kemarin", R.drawable.ic_message),
            MessageModel("Sekretaris Desa", "Mohon cek laporan bulanan yang baru saja diunggah.", "2 hari lalu", R.drawable.ic_message)
        )

        // Data Dummy untuk Notifikasi
        val notifList = listOf(
            MessageModel("Sistem", "Laporan kegiatan berhasil diverifikasi.", "10:30", R.drawable.ic_message),
            MessageModel("Pemberitahuan", "Ada pembaruan sistem administrasi desa v1.2.", "Senin", R.drawable.ic_message)
        )

        // Setup RecyclerView
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MessageAdapter(pesanList)
        binding.rvMessages.adapter = adapter

        // Setup TabLayout Listener
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.rvMessages.adapter = MessageAdapter(pesanList)
                    1 -> binding.rvMessages.adapter = MessageAdapter(notifList)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}