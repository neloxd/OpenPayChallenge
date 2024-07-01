package com.jesusvilla.movies.domain.useCase

import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.movies.data.repository.MoviesRepository
import com.jesusvilla.movies.di.MoviesRepositoryQualifier
import javax.inject.Inject

/**
 * Created by Jesús Villa on 30/06/24
 */
class GetMoviesUseCase @Inject constructor(
    @MoviesRepositoryQualifier private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(): Resource<List<BaseDataItemResponse>> {
        return moviesRepository.getMovies()
    }
}