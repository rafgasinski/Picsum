package com.example.picsum.presentation.image_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picsum.R
import com.example.picsum.data.base.PicsumImage
import com.example.picsum.databinding.ItemImageBinding

class ImageListAdapter(
    private val listener: OnImageClickListener
): ListAdapter<PicsumImage, ImageListAdapter.ImageListViewHolder>(ImageDiffUtils) {

    object ImageDiffUtils: DiffUtil.ItemCallback<PicsumImage>() {
        override fun areItemsTheSame(oldItem: PicsumImage, newItem: PicsumImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PicsumImage, newItem: PicsumImage): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onViewRecycled(holder: ImageListViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        return ImageListViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false),
            this.listener
        )
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageListViewHolder(private val binding: ItemImageBinding, private val listener: OnImageClickListener): RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageView
        fun bind(image: PicsumImage) {
            binding.run {
                this.image = image
                this.root.setOnClickListener {
                    listener.onImageClicked(image)
                }
            }
        }
    }

    interface OnImageClickListener {
        fun onImageClicked(image: PicsumImage)
    }
}