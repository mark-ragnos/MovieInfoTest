package com.example.movieinfotest.ui.popular.adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView
    var rating: TextView
    var image: ImageView
    var id: TextView


    init {
        name = itemView.findViewById(R.id.item_name)
        rating = itemView.findViewById(R.id.item_rating)
        image = itemView.findViewById(R.id.item_image)
        id = itemView.findViewById(R.id.item_id)
    }
}