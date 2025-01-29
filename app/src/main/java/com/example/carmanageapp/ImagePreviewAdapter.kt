package com.example.carmanageapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carmanageapp.databinding.ItemImagePreviewBinding

class ImagePreviewAdapter(private val images: List<Uri>) :
    RecyclerView.Adapter<ImagePreviewAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImagePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(private val binding: ItemImagePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUri: Uri) {
            Glide.with(binding.root.context)
                .load(imageUri)
                .into(binding.imageView)
        }
    }
}
