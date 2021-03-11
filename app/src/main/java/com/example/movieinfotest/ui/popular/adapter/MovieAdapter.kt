package com.example.movieinfotest.ui.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.R
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage

class MovieAdapter(val listener: MovieClickListener) :
    PagingDataAdapter<Movie, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.name.text =
            getItem(position)?.title + " (${getItem(position)?.release_date?.getYear()})"
        holder.rating.text = getItem(position)?.vote_average.toString()
        holder.id.text = getItem(position)?.id.toString()

        holder.image.registerImage(getItem(position)?.poster_path)
        holder.itemView.setOnClickListener {
            listener.OnClick(holder.id.text.toString().toInt())
        }
    }

    interface MovieClickListener {
        fun OnClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }
}