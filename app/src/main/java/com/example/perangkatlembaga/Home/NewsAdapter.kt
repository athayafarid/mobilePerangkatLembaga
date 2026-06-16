package com.example.perangkatlembaga.Home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.perangkatlembaga.databinding.ItemNewsBinding

class NewsAdapter(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.binding.apply {
            tvNewsTitle.text = news.title
            tvNewsDesc.text = news.description
            
            // Perbaikan null safety: menggunakan safe call (?.) karena image bisa bernilai null
            Glide.with(ivNewsImage.context)
                .load(news.image?.small)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.stat_notify_error)
                .centerCrop()
                .into(ivNewsImage)

            root.setOnClickListener {
                if (!news.link.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = newsList.size
}