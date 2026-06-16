package com.example.perangkatlembaga.Message

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.Message.tutorial.TutorialMessageActivity
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

        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.menu_message)
        }
        
        // Menggunakan MenuProvider (Standar terbaru Android) untuk menggantikan setHasOptionsMenu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.message_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_tutorial -> {
                        val intent = Intent(requireContext(), TutorialMessageActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Data Dummy
        val pesanList = listOf(
            MessageModel("Bapak Kades", "Undangan rapat koordinasi besok jam 9 pagi.", "08:00", R.drawable.ic_message),
            MessageModel("Ibu RW 02", "Data kependudukan baru sudah saya kirim via email.", "Kemarin", R.drawable.ic_message),
            MessageModel("Sekretaris Desa", "Mohon cek laporan bulanan yang baru saja diunggah.", "2 hari lalu", R.drawable.ic_message)
        )

        val notifList = listOf(
            MessageModel("Sistem", "Laporan kegiatan berhasil diverifikasi.", "10:30", R.drawable.ic_message),
            MessageModel("Pemberitahuan", "Ada pembaruan sistem administrasi desa v1.2.", "Senin", R.drawable.ic_message)
        )

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = MessageAdapter(pesanList)

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