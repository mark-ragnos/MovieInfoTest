package com.example.movieinfotest.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage

class PopularAdapter(
    val list: List<MovieDetailsDB>,
    val listener: MovieDetailClickListener
) : RecyclerView.Adapter<PopularAdapter.MovieDetailsDbHolder>() {


    override fun onBindViewHolder(holder: MovieDetailsDbHolder, position: Int) {
        holder.name.text = list[position].title
        holder.rating.text = list[position].vote_average.toString()
        holder.id.text = list[position].id.toString()

        holder.image.registerImage(list[position].poster_path)
        holder.itemView.setOnClickListener {
            listener.onClick(holder.id.text.toString().toInt())
        }
    }

    interface MovieDetailClickListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsDbHolder {
        return MovieDetailsDbHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }

    class MovieDetailsDbHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.item_name)
        var rating: TextView = itemView.findViewById(R.id.item_rating)
        var image: ImageView = itemView.findViewById(R.id.item_image)
        var id: TextView = itemView.findViewById(R.id.item_id)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}