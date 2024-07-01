package com.jesusvilla.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesusvilla.database.dao.MoviesDao
import com.jesusvilla.database.dao.ProfileDao
import com.jesusvilla.database.entities.movie.MovieEntity
import com.jesusvilla.database.entities.profile.KnownforEntity
import com.jesusvilla.database.entities.profile.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        KnownforEntity::class,
        MovieEntity::class
    ], version = 1, exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun movieDao(): MoviesDao
}