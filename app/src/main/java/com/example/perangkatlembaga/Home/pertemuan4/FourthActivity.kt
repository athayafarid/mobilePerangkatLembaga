package com.example.perangkatlembaga.Home.pertemuan4

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.data.local.AppDatabase
import com.example.perangkatlembaga.data.local.AnggotaEntity
import com.example.perangkatlembaga.databinding.ActivityFourthBinding
import kotlinx.coroutines.launch

class FourthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFourthBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: AnggotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Anggota Lembaga"

        setupRecyclerView()

        binding.btnSaveAnggota.setOnClickListener {
            val nama = binding.etNamaAnggota.text.toString()
            val jabatan = binding.etJabatan.text.toString()

            if (nama.isNotEmpty() && jabatan.isNotEmpty()) {
                val anggota = AnggotaEntity(nama = nama, jabatan = jabatan)
                lifecycleScope.launch {
                    database.anggotaDao().insertAnggota(anggota)
                    binding.etNamaAnggota.text?.clear()
                    binding.etJabatan.text?.clear()
                    loadData()
                    Toast.makeText(this@FourthActivity, "Anggota Berhasil Ditambah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Mohon isi nama dan jabatan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = AnggotaAdapter(listOf()) { anggota ->
            lifecycleScope.launch {
                database.anggotaDao().deleteAnggota(anggota)
                loadData()
                Toast.makeText(this@FourthActivity, "Data Dihapus", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvAnggota.layoutManager = LinearLayoutManager(this)
        binding.rvAnggota.adapter = adapter
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val list = database.anggotaDao().getAllAnggota()
            adapter.updateData(list)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}