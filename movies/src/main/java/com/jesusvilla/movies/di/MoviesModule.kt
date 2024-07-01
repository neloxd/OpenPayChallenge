package com.jesusvilla.movies.di

import com.jesusvilla.core.network.connection.RetrofitApi
import com.jesusvilla.movies.data.repository.MoviesRepository
import com.jesusvilla.movies.data.repository.MoviesRepositoryImpl
import com.jesusvilla.movies.data.service.MoviesAPI
import com.jesusvilla.movies.domain.useCase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MoviesModule {
    @Provides
    @ViewModelScoped
    @MoviesQualifier
    fun provideMoviesApi(@RetrofitApi retrofit: Retrofit): MoviesAPI {
        return retrofit.create(MoviesAPI::class.java)
    }

    @Provides
    @ViewModelScoped
    @MoviesRepositoryQualifier
    fun provideMoviesRepository(@MoviesQualifier moviesAPI: MoviesAPI): MoviesRepository {
        return MoviesRepositoryImpl(moviesAPI)
    }

    @Provides
    @ViewModelScoped
    @GetMoviesUseCaseQualifier
    fun provideGetMoviesUseCase(@MoviesRepositoryQualifier moviesRepository: MoviesRepository): GetMoviesUseCase {
        return GetMoviesUseCase(moviesRepository)
    }
}