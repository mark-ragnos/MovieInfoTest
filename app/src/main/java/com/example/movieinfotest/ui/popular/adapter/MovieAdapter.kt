package com.example.movieinfotest.ui.popular.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieAdapter(val listener: MovieClickListener, val repository: Repository) :
    PagingDataAdapter<Movie, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.name.text =
            getItem(position)?.title + " (${getItem(position)?.release_date?.getYear()})"
        holder.rating.text = getItem(position)?.vote_average.toString()
        holder.id.text = getItem(position)?.id.toString()

        holder.image.registerImage(getItem(position)?.poster_path)
        holder.itemView.setOnClickListener {
            listener.onClick(holder.id.text.toString().toInt())
        }
        if(!MainActivity.isLogin())
            holder.favorite.visibility = View.VISIBLE

        holder.favorite.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isFavorite = isFavorite(position)
                listener.onFavorite(getItem(position), isFavorite)
                settingImage(holder, position, !isFavorite)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = isFavorite(position)
            settingImage(holder, position, isFavorite)
        }
    }

    interface MovieClickListener {
        fun onClick(id: Int)
        fun onFavorite(movie: Movie?, isFavorite: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }

    private suspend fun isFavorite(position: Int): Boolean {
        return repository.isFavorite(getItem(position)!!.id)

    }

    private suspend fun settingImage(
        holder: MovieHolder,
        position: Int,
        isFavorite: Boolean = false
    ) {
        if (isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_favorite)
        } else
            holder.favorite.setImageResource(R.drawable.ic_favorite_not)
    }
}