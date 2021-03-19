package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository

class RandomMovieUseCase(
    private val genreRepository: IGenreRepository<Genre>,
    private val movieRepository: IMovieRepository<Movie>
) {

    suspend fun getGenres(): List<Genre>? {
        return genreRepository.getGenres()
    }

    suspend fun getRandomMovie(genre: String, year: String): Movie{
        return movieRepository.getRandomMovie(genre, year)
    }
}