package com.example.perangkatlembaga.Home.pertemuan4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perangkatlembaga.data.local.AnggotaEntity
import com.example.perangkatlembaga.databinding.ItemAnggotaBinding

class AnggotaAdapter(
    private var anggotaList: List<AnggotaEntity>,
    private val onDeleteClick: (AnggotaEntity) -> Unit
) : RecyclerView.Adapter<AnggotaAdapter.AnggotaViewHolder>() {

    class AnggotaViewHolder(val binding: ItemAnggotaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaViewHolder {
        val binding = ItemAnggotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnggotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnggotaViewHolder, position: Int) {
        val anggota = anggotaList[position]
        holder.binding.apply {
            tvNamaAnggota.text = anggota.nama
            tvJabatan.text = anggota.jabatan
            btnDelete.setOnClickListener { onDeleteClick(anggota) }
        }
    }

    override fun getItemCount(): Int = anggotaList.size

    fun updateData(newList: List<AnggotaEntity>) {
        anggotaList = newList
        notifyDataSetChanged()
    }
}