package com.example.perangkatlembaga.Home.pertemuan2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perangkatlembaga.data.local.RTEntity
import com.example.perangkatlembaga.databinding.ItemRtBinding

class RTAdapter(
    private var rtList: List<RTEntity>,
    private val onDeleteClick: (RTEntity) -> Unit
) : RecyclerView.Adapter<RTAdapter.RTViewHolder>() {

    class RTViewHolder(val binding: ItemRtBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RTViewHolder {
        val binding = ItemRtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RTViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RTViewHolder, position: Int) {
        val rt = rtList[position]
        holder.binding.apply {
            tvNomorRT.text = "RT ${rt.nomorRT}"
            tvKetuaRT.text = rt.ketuaRT
            btnDelete.setOnClickListener { onDeleteClick(rt) }
        }
    }

    override fun getItemCount(): Int = rtList.size

    fun updateData(newList: List<RTEntity>) {
        rtList = newList
        notifyDataSetChanged()
    }
}