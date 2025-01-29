package com.example.carmanageapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carmanageapp.databinding.ItemCarBinding
import com.example.carmanageapp.models.CarResponse

class CarAdapter(private val onCarClicked: (CarResponse) -> Unit) :
    ListAdapter<CarResponse, CarAdapter.CarViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        car?.let {
            holder.bind(it)
        }
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(car: CarResponse) {
            binding.carTitle.text = car.title
            binding.carDescription.text = car.description
            binding.carTags.text = car.tags.joinToString(", ")  // Display tags as comma-separated

            // Load the first image of the car using Glide
            if (car.image.isNotEmpty()) {
                Glide.with(binding.carImage.context)
                    .load(car.image[0])  // Load first image
                    .into(binding.carImage)
            }

            binding.root.setOnClickListener {
                onCarClicked(car)  // Trigger click callback
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<CarResponse>() {
        override fun areItemsTheSame(oldItem: CarResponse, newItem: CarResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: CarResponse, newItem: CarResponse): Boolean {
            return oldItem == newItem
        }
    }
}
