package com.example.perangkatlembaga.Home.pertemuan3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perangkatlembaga.data.local.RWEntity
import com.example.perangkatlembaga.databinding.ItemRwBinding

class RWAdapter(
    private var rwList: List<RWEntity>,
    private val onDeleteClick: (RWEntity) -> Unit
) : RecyclerView.Adapter<RWAdapter.RWViewHolder>() {

    class RWViewHolder(val binding: ItemRwBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RWViewHolder {
        val binding = ItemRwBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RWViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RWViewHolder, position: Int) {
        val rw = rwList[position]
        holder.binding.apply {
            tvNomorRW.text = "RW ${rw.nomorRW}"
            tvKetuaRW.text = rw.ketuaRW
            btnDelete.setOnClickListener { onDeleteClick(rw) }
        }
    }

    override fun getItemCount(): Int = rwList.size

    fun updateData(newList: List<RWEntity>) {
        rwList = newList
        notifyDataSetChanged()
    }
}