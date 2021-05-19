package com.example.movieinfotest.presentation.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val popularMovieUseCase: PopularMovieUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private val movies: Flow<PagingData<MovieDomain>> =
        popularMovieUseCase.getPopularList().cachedIn(viewModelScope)

    fun getFavorite(): Flow<PagingData<MovieDomain>> {
        return movies
    }

    fun saveInFavorite(movie: MovieDomain, sourceMode: NetworkConnection.STATUS) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.saveInFavorite(movie, sourceMode)
        }
    }

    fun removeFromFavorite(movie: MovieDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.deleteFromFavorite(movie.id)
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return favoriteMovieUseCase.isFavorite(id)
    }
}
