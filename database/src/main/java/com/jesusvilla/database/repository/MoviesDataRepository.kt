package com.jesusvilla.database.repository

import androidx.annotation.WorkerThread
import com.jesusvilla.database.dao.MoviesDao
import com.jesusvilla.database.di.qualifiers.MovieDaoQualifier
import com.jesusvilla.database.entities.movie.MovieEntity
import com.jesusvilla.database.resources.DataBaseResource
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
    @MovieDaoQualifier private val moviesDao: MoviesDao
) {

    @WorkerThread
    suspend fun getMovieList(): DataBaseResource<List<MovieEntity>> {
        return try {
            DataBaseResource.Success(moviesDao.getMovies())
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun deleteAll(): DataBaseResource<Boolean> {
        return try {
            moviesDao.deleteMovies()
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun insertMovies(
        list: List<MovieEntity>
    ) {
        deleteAll()
        moviesDao.insertMovieList(list)
    }
}