package com.example.movieinfotest.data.repositories

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.utils.DataSourceMode
import com.example.movieinfotest.utils.toGenreDomain

class GenreRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IGenreRepository<Genre> {
    override suspend fun getGenres(dataSourceMode: DataSourceMode): List<Genre>? {
        if (dataSourceMode == DataSourceMode.ONLINE) {
            val genreDb = db.getAllGenres()
            if(genreDb.isNullOrEmpty())
                return genreDb?.toGenreDomain()
            val genres = api.getGenresList()
            if (!genres.isNullOrEmpty())
                db.addAllGenres(genres)
        }
        return db.getAllGenres()?.toGenreDomain()
    }
}