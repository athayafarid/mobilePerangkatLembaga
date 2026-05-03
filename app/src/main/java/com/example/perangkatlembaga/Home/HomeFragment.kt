package com.example.perangkatlembaga.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.perangkatlembaga.Home.pertemuan2.SecondActivity
import com.example.perangkatlembaga.Home.pertemuan3.ThirdActivity
import com.example.perangkatlembaga.Home.pertemuan4.FourthActivity
import com.example.perangkatlembaga.Home.pertemuan7.SevenActivity
import com.example.perangkatlembaga.LoginActivity
import com.example.perangkatlembaga.databinding.FragmentHomeBinding
import com.example.perangkatlembaga.pertemuan_5.FifthActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Pertemuan 2 -> SecondActivity
        binding.btnPertemuan2.setOnClickListener {
            startActivity(Intent(requireContext(), SecondActivity::class.java))
        }

        // Pertemuan 3 -> ThirdActivity
        binding.btnPertemuan3.setOnClickListener {
            startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }

        // Pertemuan 4 -> FourthActivity
        binding.btnPertemuan4.setOnClickListener {
            val intent = Intent(requireContext(), FourthActivity::class.java)
            intent.putExtra("name", "Politeknik Caltex Riau")
            intent.putExtra("from", "Rumbai")
            intent.putExtra("age", 25)
            startActivity(intent)
        }

        // Pertemuan 5 -> FifthActivity
        binding.btnPertemuan5.setOnClickListener {
            startActivity(Intent(requireContext(), FifthActivity::class.java))
        }

        // Pertemuan 7 -> SevenActivity (New Package)
        binding.btnPertemuan7.setOnClickListener {
            startActivity(Intent(requireContext(), SevenActivity::class.java))
        }

        // Logout Button
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()

                    with(sharedPref.edit()) {
                        putBoolean("isLogin", false)
                        apply()
                    }
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }

}

