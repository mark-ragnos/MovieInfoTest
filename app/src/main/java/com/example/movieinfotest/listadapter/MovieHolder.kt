package com.example.movieinfotest.listadapter

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ActivityMainBinding

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name = itemView.findViewById<TextureView>(R.id.item_name)
    private val rating = itemView.findViewById<TextureView>(R.id.item_rating)
    private val image = itemView.findViewById<ImageView>(R.id.item_image)


    init {

    }
}