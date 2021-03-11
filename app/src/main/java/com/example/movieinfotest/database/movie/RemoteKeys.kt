package com.example.movieinfotest.database.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val Id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
