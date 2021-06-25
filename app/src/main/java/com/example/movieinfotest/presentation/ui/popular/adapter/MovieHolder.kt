package com.example.movieinfotest.presentation.ui.popular.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.displayMoviePoster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class MovieHolder(
    private val binding: ItemListBinding,
    private val listener: MovieAdapter.MovieClickListener,
    isVisibleFavoriteButton: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    private val movie = MutableStateFlow<MovieDomain?>(null)
    private val isFavorite = MutableStateFlow(false)

    init {
        binding.itemFavorite.setOnClickListener {
            movie.value?.let { it1 ->
                listener.favoriteAddOrRemove(it1, isFavorite.value)
            }
            isFavorite.value = !isFavorite.value
        }

        if (isVisibleFavoriteButton) {
            binding.itemFavorite.visibility = View.VISIBLE
        }

        observeMovie()
        observeFavorite()
    }

    private fun observeMovie() = CoroutineScope(Dispatchers.Main).launch {
        movie.collectLatest { movie ->
            binding.apply {
                movie?.let {
                    itemId.text = it.id.toString()
                    itemName.text = it.title
                    itemRating.text = it.voteAverage.toString()
                    itemImage.displayMoviePoster(it.posterPath, SIZE_X, SIZE_Y)
                    itemView.setOnClickListener { listener.navigate(getId()) }
                }
            }
        }
    }

    private fun observeFavorite() = CoroutineScope(Dispatchers.Main).launch {
        isFavorite.collectLatest {
            changeImage(it)
        }
    }

    fun bind(movie: MovieDomain?) {
        this.movie.value = movie
        isFavorite()
    }

    private fun isFavorite() = CoroutineScope(Dispatchers.IO).launch {
        isFavorite.value = listener.isFavorite(movie.value?.id ?: 0)
    }

    private fun getId(): Int {
        return movie.value?.id ?: 0
    }

    private fun changeImage(isFavorite: Boolean) {
        if (isFavorite) {
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite_not)
        }
    }

    companion object {
        private const val SIZE_X = 75
        private const val SIZE_Y = 100
    }
}
