package com.example.movieinfotest.data.entities.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movieinfotest.data.entities.actors.Cast
import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.GenreMovieDB

class FavoriteMovie(
    @Embedded val movie: MovieDetailsDB,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val casts: List<Cast>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val crew: List<Crew>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genres: List<GenreMovieDB>
)
