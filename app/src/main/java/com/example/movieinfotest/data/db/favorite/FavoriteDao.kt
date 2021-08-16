package com.example.movieinfotest.data.db.favorite

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Dao
import com.example.movieinfotest.data.entities.actors.Cast
import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.data.entities.db.FavoriteMovie
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.GenreMovieDB
import com.example.movieinfotest.utils.converters.toGenreDB
import com.example.movieinfotest.utils.toMovieDetailsDB
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCasts(cast: List<Cast>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCrews(crews: List<Crew>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genreDBS: List<GenreMovieDB>)

    @Query("SELECT * FROM genremoviedb WHERE movieId LIKE :movieId")
    suspend fun getGenres(movieId: Int): List<GenreMovieDB>

    @Transaction
    suspend fun saveInFavorite(movieDetails: MovieDetails, casts: List<Cast>?, crews: List<Crew>?) {
        val genres = movieDetails.genres?.toGenreDB(movieDetails.id)
        val finCast = casts?.map {
            Cast(movieDetails.id, it.id, it.name, it.character, it.profilePath, it.gender)
        }
        val finCrew = crews?.map {
            Crew(movieDetails.id, it.id, it.name, it.job, it.profilePath, it.gender)
        }

        val details = movieDetails.toMovieDetailsDB()
        details.addDate = Calendar.getInstance().time.time

        addMovieDetails(details)

        if (finCast != null) {
            addCasts(finCast)
        }
        if (genres != null) {
            addGenres(genres)
        }
        if (finCrew != null) {
            addCrews(finCrew)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieDetails(movieDetails: MovieDetailsDB)

    @Query("SELECT * FROM moviedetailsdb ORDER BY addDate DESC")
    fun getFavoriteList(): PagingSource<Int, MovieDetailsDB>

    @Transaction
    @Query("SELECT * FROM moviedetailsdb WHERE id LIKE :movieId")
    suspend fun getFavoriteById(movieId: Int): FavoriteMovie?

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

    @Query("DELETE FROM `cast` WHERE movieId LIKE :movieId")
    suspend fun deleteActors(movieId: Int)

    @Query("DELETE FROM genremoviedb WHERE movieId LIKE :movieId")
    suspend fun deleteGenres(movieId: Int)

    @Query("SELECT id FROM moviedetailsdb")
    fun getAllFavoriteIds(): Flow<List<Int>>
}
