package com.example.movieinfotest.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM RemoteKeys WHERE Id = :id")
    fun remoteKeysById(id: Int): RemoteKeys?

    @Query("DELETE FROM RemoteKeys")
    fun clearRemoteKeys()
}