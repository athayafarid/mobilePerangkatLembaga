package com.example.perangkatlembaga.Home.pertemuan2

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perangkatlembaga.data.local.AppDatabase
import com.example.perangkatlembaga.data.local.RTEntity
import com.example.perangkatlembaga.databinding.ActivitySecondBinding
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: RTAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Manajemen Data RT"

        setupRecyclerView()

        binding.btnSave.setOnClickListener {
            val nomorRT = binding.etNomorRT.text.toString()
            val ketuaRT = binding.etKetuaRT.text.toString()

            if (nomorRT.isNotEmpty() && ketuaRT.isNotEmpty()) {
                val rt = RTEntity(nomorRT = nomorRT, ketuaRT = ketuaRT)
                lifecycleScope.launch {
                    database.rtDao().insertRT(rt)
                    binding.etNomorRT.text?.clear()
                    binding.etKetuaRT.text?.clear()
                    loadData()
                    Toast.makeText(this@SecondActivity, "Data RT Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = RTAdapter(listOf()) { rt ->
            lifecycleScope.launch {
                database.rtDao().deleteRT(rt)
                loadData()
                Toast.makeText(this@SecondActivity, "Data RT Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvRT.layoutManager = LinearLayoutManager(this)
        binding.rvRT.adapter = adapter
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val list = database.rtDao().getAllRT()
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