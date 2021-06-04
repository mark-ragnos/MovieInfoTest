package com.example.movieinfotest.data.repositories

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.network.isOnline
import com.example.movieinfotest.utils.converters.toGenreDomain

class GenreRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IGenreRepository<GenreDomain> {
    override suspend fun getGenres(networkStatus: NetworkConnection.STATUS): List<GenreDomain>? {
        if (!networkStatus.isOnline()) {
            return db.getAllGenres()?.toGenreDomain()
        }

        val netList = api.getGenresList()
        val dbList = db.getAllGenres()

        if (netList?.size == dbList?.size) {
            return dbList?.toGenreDomain()
        }

        db.addAllGenres(netList!!)
        return dbList?.toGenreDomain()
    }
}
