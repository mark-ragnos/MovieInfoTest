package com.example.movieinfotest.data.repositories

import com.example.movieinfotest.data.api.MovieHelper
import com.example.movieinfotest.data.db.DatabaseHelper
import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.utils.toGenreDomain

class GenreRepository(
    val api: MovieHelper,
    val db: DatabaseHelper
):IGenreRepository<Genre> {
    override suspend fun getGenres(): List<Genre>? {
        val genres = api.getGenresList()
        if(!genres.isNullOrEmpty())
            db.addAllGenres(genres)

        return db.getAllGenres()?.toGenreDomain()
    }
}