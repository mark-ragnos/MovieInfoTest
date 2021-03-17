package com.example.movieinfotest.ui.popular.adapter


import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.item_name)
    var rating: TextView = itemView.findViewById(R.id.item_rating)
    var image: ImageView = itemView.findViewById(R.id.item_image)
    var id: TextView = itemView.findViewById(R.id.item_id)
    var favorite: ImageButton = itemView.findViewById(R.id.item_favorite)
}