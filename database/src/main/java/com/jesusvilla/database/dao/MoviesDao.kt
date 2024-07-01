package com.jesusvilla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jesusvilla.database.entities.movie.MovieEntity

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM MOVIE")
    suspend fun getMovies(): List<MovieEntity>

    @Query("DELETE FROM MOVIE")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(list: List<MovieEntity>): List<Long>
}