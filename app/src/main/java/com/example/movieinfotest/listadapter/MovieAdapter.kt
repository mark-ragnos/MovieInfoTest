package com.example.movieinfotest.listadapter

import Results
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.getYear
import com.squareup.picasso.Picasso

class MovieAdapter : PagingDataAdapter<Results, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.name.text =
            getItem(position)?.title + " (${getItem(position)?.release_date?.getYear()})"
        holder.rating.text = getItem(position)?.vote_average.toString()

        //Разница между шириной и высотой 1.5
        Picasso.get()
            .load("https://www.themoviedb.org/t/p/w1280${getItem(position)?.poster_path}")
            .resize(60, 90)
            .centerCrop()
            .into(holder.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }
}