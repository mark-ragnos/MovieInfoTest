package com.example.movieinfotest.presentation.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.data.entities.popular.Movie
import com.example.movieinfotest.utils.toMovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        repository.getPopular().cachedIn(viewModelScope)

    fun getFavorite(): Flow<PagingData<Movie>> {
        return movies
    }

    fun saveInFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveInFavorite(movie.toMovieDetails(), null)
        }
    }

    fun removeFromFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFromFavorite(movie.id)
        }
    }
}