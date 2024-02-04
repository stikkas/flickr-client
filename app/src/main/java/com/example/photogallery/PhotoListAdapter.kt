package com.example.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.photogallery.databinding.ListItemGalleryBinding
import com.example.photogallery.model.GallaryItem

class PhotoViewHolder(private val binding: ListItemGalleryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryItem: GallaryItem) {
        binding.itemImageView.load(galleryItem.url) {
            placeholder(R.drawable.bill_up_close)
        }
    }
}

class PhotoListAdapter(private val galleryItems: List<GallaryItem>) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return galleryItems.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = galleryItems[position]
        holder.bind(item)
    }
}
