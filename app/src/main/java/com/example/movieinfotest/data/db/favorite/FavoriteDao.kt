package com.example.movieinfotest.data.db.favorite

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Dao
import com.example.movieinfotest.data.entities.actors.Actor
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.GenreDB
import com.example.movieinfotest.utils.toGenreDB
import com.example.movieinfotest.utils.toMovieDetailsDB
import java.util.Calendar

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActors(actor: List<Actor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genreDBS: List<GenreDB>)

    @Query("SELECT * FROM actor WHERE movieId LIKE :movieId")
    suspend fun getActors(movieId: Int): List<Actor>

    @Query("SELECT * FROM genredb WHERE movieId LIKE :movieId")
    suspend fun getGenres(movieId: Int): List<GenreDB>

    @Transaction
    suspend fun saveInFavorite(movieDetails: MovieDetails, actors: List<Actor>?) {
        val genres = movieDetails.genres?.map {
            it.toGenreDB(movieDetails.id)
        }
        val finActors = actors?.map {
            Actor(movieDetails.id, it.id, it.name, it.character, it.profilePath, it.gender)
        }

        val details = movieDetails.toMovieDetailsDB()
        details.addDate = Calendar.getInstance().time.time

        addMovieDetails(details)

        if (finActors != null) {
            addActors(finActors)
        }
        if (genres != null) {
            addGenres(genres)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieDetails(movieDetails: MovieDetailsDB)

    @Query("SELECT * FROM moviedetailsdb ORDER BY addDate DESC")
    fun getFavoriteList(): PagingSource<Int, MovieDetailsDB>

    @Query("SELECT * FROM moviedetailsdb WHERE id LIKE :movieId")
    suspend fun getFavoriteById(movieId: Int): MovieDetailsDB?

    @Query("SELECT * FROM moviedetailsdb WHERE id LIKE :movieId")
    fun getFavorite(movieId: Int): Int

    @Transaction
    suspend fun removeFromFavorite(movieId: Int) {
        deleteFavorite(movieId)
        deleteGenres(movieId)
        deleteActors(movieId)
    }

    @Query("DELETE FROM moviedetailsdb WHERE id LIKE :movieId")
    suspend fun deleteFavorite(movieId: Int)

    @Query("DELETE FROM actor WHERE movieId LIKE :movieId")
    suspend fun deleteActors(movieId: Int)

    @Query("DELETE FROM genredb WHERE movieId LIKE :movieId")
    suspend fun deleteGenres(movieId: Int)
}
