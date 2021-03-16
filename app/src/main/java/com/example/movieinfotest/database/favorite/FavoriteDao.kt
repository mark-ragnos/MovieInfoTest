package com.example.movieinfotest.database.favorite

import androidx.room.*
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.genre.GenreDB
import com.example.movieinfotest.utils.toGenreDB
import com.example.movieinfotest.utils.toMovieDetailsDB

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActors(actor: List<Actor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genreDBS: List<GenreDB>)

    @Query("SELECT * FROM actor WHERE movie_id LIKE :movie_id")
    suspend fun getActors(movie_id: Int): List<Actor>?

    @Query("SELECT * FROM genredb WHERE movie_id LIKE :movie_id")
    suspend fun getGenres(movie_id: Int): List<GenreDB>?

    @Transaction
    suspend fun saveInFavorite(movieDetails: MovieDetails, actors: List<Actor>?) {
        val genres = movieDetails.genres?.map {
            it.toGenreDB(movieDetails.id)
        }
        val finActors = actors?.map {
            Actor(movieDetails.id, it.id, it.name, it.character, it.profile_path)
        }

        val details = movieDetails.toMovieDetailsDB()

        addMovieDetails(details)
        if (finActors != null)
            addActors(finActors)
        if (genres != null)
            addGenres(genres)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieDetails(movieDetails: MovieDetailsDB)

    @Query("SELECT * FROM moviedetailsdb")
    suspend fun getFavoriteList(): List<MovieDetailsDB>?

    @Query("SELECT * FROM moviedetailsdb WHERE id LIKE :id")
    suspend fun getFavoriteById(id: Int): MovieDetailsDB?
}
