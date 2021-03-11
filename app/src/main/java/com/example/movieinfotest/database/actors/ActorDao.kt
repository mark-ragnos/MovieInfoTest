package com.example.movieinfotest.database.actors

import androidx.room.Dao
import androidx.room.Insert
import com.example.movieinfotest.models.actors.Actor


@Dao
interface ActorDao {

    @Insert
    fun addActor(actor: Actor)
}