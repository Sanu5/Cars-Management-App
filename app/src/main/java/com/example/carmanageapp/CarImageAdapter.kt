package com.example.carmanageapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carmanageapp.databinding.ItemCarImageBinding

class CarImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CarImageAdapter.CarImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarImageViewHolder {
        val binding = ItemCarImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class CarImageViewHolder(private val binding: ItemCarImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.carImage.context)
                .load(imageUrl)
                .into(binding.carImage)
        }
    }
}
