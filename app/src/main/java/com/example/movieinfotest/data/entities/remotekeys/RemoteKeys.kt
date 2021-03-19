package com.example.movieinfotest.data.entities.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val Id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
