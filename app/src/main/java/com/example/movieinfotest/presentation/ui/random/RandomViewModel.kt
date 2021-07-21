package com.example.movieinfotest.presentation.ui.random

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.utils.NOT_SELECTED_GENRE
import com.example.movieinfotest.utils.RANDOM_LIST_SIZE
import com.example.movieinfotest.utils.network.NetworkConnection
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

    private val _selectedGenre = MutableStateFlow(GenreDomain(0, "Select genre"))
    val selectedGenre = _selectedGenre.asStateFlow()

    fun setGenre(genre: GenreDomain) {
        viewModelScope.launch {
            _selectedGenre.emit(genre)
        }
    }

    private val _year = MutableStateFlow("")
    val year = _year.asStateFlow()

    fun setYear(year: String) {
        viewModelScope.launch {
            _year.emit(year)
        }
    }

    //    fun setSelectedGenre(id: Int) {
//        viewModelScope.launch {
//            _selectedGenreId.emit(id)
//        }
//    }
//
    fun clearSelectedGenre() {
        viewModelScope.launch {
            _selectedGenre.emit(GenreDomain(0, "Select genre"))
            _year.emit("")
        }
    }
//
//    fun generateRandom(year: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _movies.emit(
//                movieUseCase.getRandomMovie(
//                    genre = if (selectedGenreId.value == NOT_SELECTED_GENRE) "" else selectedGenreId.value.toString(),
//                    year = year
//                )
//            )
//        }
//    }

    fun loadGenres() {
        if (genres.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _genres.value = genreUseCase.getAllGenres(NetworkConnection.STATUS.ONLINE)
            }
        }
    }
}
