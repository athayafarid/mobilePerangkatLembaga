package com.example.perangkatlembaga.Home.pertemuan3

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.data.local.AppDatabase
import com.example.perangkatlembaga.data.local.RWEntity
import com.example.perangkatlembaga.databinding.ActivityThirdBinding
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: RWAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Manajemen Data RW"

        setupRecyclerView()

        binding.btnSaveRW.setOnClickListener {
            val nomorRW = binding.etNomorRW.text.toString()
            val ketuaRW = binding.etKetuaRW.text.toString()

            if (nomorRW.isNotEmpty() && ketuaRW.isNotEmpty()) {
                val rw = RWEntity(nomorRW = nomorRW, ketuaRW = ketuaRW)
                lifecycleScope.launch {
                    database.rwDao().insertRW(rw)
                    binding.etNomorRW.text?.clear()
                    binding.etKetuaRW.text?.clear()
                    loadData()
                    Toast.makeText(this@ThirdActivity, "Data RW Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = RWAdapter(listOf()) { rw ->
            lifecycleScope.launch {
                database.rwDao().deleteRW(rw)
                loadData()
                Toast.makeText(this@ThirdActivity, "Data RW Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvRW.layoutManager = LinearLayoutManager(this)
        binding.rvRW.adapter = adapter
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val list = database.rwDao().getAllRW()
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