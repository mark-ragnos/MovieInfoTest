package com.example.movieinfotest.presentation.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieinfotest.old.Repository
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.utils.toMovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val popularMovieUseCase: PopularMovieUseCase,
    val favoriteMovieUseCase: FavoriteMovieUseCase
    ) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        popularMovieUseCase.getPopularList().cachedIn(viewModelScope)

    fun getFavorite(): Flow<PagingData<Movie>> {
        return movies
    }

    fun saveInFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
             favoriteMovieUseCase.saveInFavorite(movie)
        }
    }

    fun removeFromFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteMovieUseCase.deleteFromFavorite(movie.id)
        }
    }
}