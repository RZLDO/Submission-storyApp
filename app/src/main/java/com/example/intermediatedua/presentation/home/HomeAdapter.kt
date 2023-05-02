package com.example.intermediatedua.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intermediatedua.R
import com.example.intermediatedua.data.home.ListStoryItem

class HomeAdapter(private val listItems : List<ListStoryItem>): RecyclerView.Adapter<HomeAdapter.ViewModel>() {
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                listener(position)
            }
        }
    }

    inner class ViewModel(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_imageStory)
        val userName: TextView = view.findViewById(R.id.tv_username)
        val detail: TextView = view.findViewById(R.id.tv_description)
        fun bind(listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_list, parent, false)
        return ViewModel(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewModel, position: Int) {
        val storyData = listItems[position]
        Glide.with(holder.itemView.context)
            .load(storyData.photoUrl)
            .into(holder.imageView)
        holder.userName.text = storyData.name
        holder.detail.text = storyData.description
        onItemClickListener?.let { listener ->
            holder.bind(listener)
        }
    }

    override fun getItemCount(): Int = listItems.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}