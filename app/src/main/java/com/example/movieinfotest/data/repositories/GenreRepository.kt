package com.example.movieinfotest.data.repositories

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.network.isOnline
import com.example.movieinfotest.utils.toGenreDomain
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GenreRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IGenreRepository<GenreDomain> {
    override suspend fun getGenres(networkStatus: NetworkConnection.STATUS): List<GenreDomain>? {
        if (networkStatus.isOnline())
            return db.getAllGenres()?.toGenreDomain()

        var result: List<GenreDomain>? = null

        coroutineScope {
            val dbGenreDef = async {
                db.getAllGenres()
            }
            val apiGenreDef = async {
                api.getGenresList()
            }

            val dbGenres = dbGenreDef.await()
            val apiGenres = apiGenreDef.await()

            result = if (dbGenres?.size == apiGenres?.size)
                dbGenres?.toGenreDomain()
            else {
                apiGenres?.let { db.addAllGenres(it) }
                apiGenres?.toGenreDomain()
            }
        }

        return result
    }
}
