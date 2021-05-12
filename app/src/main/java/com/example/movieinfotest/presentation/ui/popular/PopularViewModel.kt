package com.example.movieinfotest.presentation.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.utils.network.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val popularMovieUseCase: PopularMovieUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        popularMovieUseCase.getPopularList().cachedIn(viewModelScope)

    fun getFavorite(): Flow<PagingData<Movie>> {
        return movies
    }

    fun saveInFavorite(movie: Movie, sourceMode: NetworkStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.saveInFavorite(movie, sourceMode)
        }
    }

    fun removeFromFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.deleteFromFavorite(movie.id)
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return favoriteMovieUseCase.isFavorite(id)
    }
}