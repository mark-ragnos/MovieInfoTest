package com.example.movieinfotest.presentation.ui.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RandomViewModel(
    private val movieUseCase: MovieUseCase,
    private val genreUseCase: GenreUseCase
) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieDomain>>(listOf())
    val movies = _movies.asStateFlow()

    private val _genres = MutableStateFlow<List<GenreDomain>?>(null)
    val genres = _genres.asStateFlow()

    private val _selectedGenre = MutableStateFlow(GenreDomain(0, ""))
    val selectedGenre = _selectedGenre.asStateFlow()

    private val _progressAdded = MutableStateFlow(false)
    val progressAdded = _progressAdded.asStateFlow()

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

    fun clearSelectedGenre() {
        viewModelScope.launch {
            _selectedGenre.emit(GenreDomain(0, ""))
            _year.emit("")
        }
    }

    fun generateRandom(genre: GenreDomain, year: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _progressAdded.emit(true)
            val movie = movieUseCase.getRandomMovie(
                genre = if (genre.id == 0) "" else genre.id.toString(),
                year = year
            )
            _movies.emit(movies.value + listOf(movie))
            _progressAdded.emit(false)
        }
    }

    fun loadGenres() {
        if (genres.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _genres.value = genreUseCase.getAllGenres(NetworkConnection.STATUS.ONLINE)
            }
        }
    }
}
