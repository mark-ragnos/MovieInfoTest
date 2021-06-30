package com.example.movieinfotest.presentation.ui.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.utils.NOT_SELECTED_GENRE
import com.example.movieinfotest.utils.RANDOM_LIST_SIZE
import com.example.movieinfotest.utils.network.NetworkConnection
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RandomViewModel(
    private val movieUseCase: MovieUseCase,
    private val genreUseCase: GenreUseCase
) : ViewModel() {
    private val _movies = MutableSharedFlow<MovieDomain>(
        extraBufferCapacity = RANDOM_LIST_SIZE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = RANDOM_LIST_SIZE
    )
    val movies = _movies.asSharedFlow()

    private val _genres = MutableStateFlow<List<GenreDomain>?>(null)
    val genres = _genres.asStateFlow()

    private val _selectedGenreId = MutableStateFlow(NOT_SELECTED_GENRE)
    val selectedGenreId = _selectedGenreId.asStateFlow()

    val selectGenreListener =
        OnSpinnerItemSelectedListener<GenreDomain> { _, _, _, newItem ->
            viewModelScope.launch {
                _selectedGenreId.emit(newItem.id)
            }
        }

    fun generateRandom(year: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.emit(
                movieUseCase.getRandomMovie(
                    genre = if (selectedGenreId.value == NOT_SELECTED_GENRE) "" else selectedGenreId.value.toString(),
                    year = year
                )
            )
        }
    }

    fun loadGenres(networkStatus: NetworkConnection.STATUS) {
        if (genres.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _genres.value = genreUseCase.getAllGenres(networkStatus)
            }
        }
    }
}
