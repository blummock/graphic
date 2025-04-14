package com.blummock.graphic.recycler

import android.annotation.SuppressLint
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blummock.graphic.databinding.ItemPointBinding

internal class PointsAdapter : ListAdapter<PointF, PointsAdapter.PointsViewHolder>(
    object : DiffUtil.ItemCallback<PointF>() {
        override fun areItemsTheSame(oldItem: PointF, newItem: PointF): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PointF, newItem: PointF): Boolean {
            return oldItem.x == newItem.x && oldItem.y == oldItem.y
        }
    }
) {

    inner class PointsViewHolder(private val binding: ItemPointBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PointF) {
            binding.point.text = "X${item.x}, Y${item.y}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val binding = ItemPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}