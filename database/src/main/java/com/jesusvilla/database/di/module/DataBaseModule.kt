package com.jesusvilla.database.di.module

import android.content.Context
import androidx.room.Room
import com.jesusvilla.database.di.qualifiers.ProfileDaoQualifier
import com.jesusvilla.database.di.qualifiers.ProfileDBRepositoryQualifier
import com.jesusvilla.database.DataBase
import com.jesusvilla.database.constants.dataBaseConfig
import com.jesusvilla.database.dao.MoviesDao
import com.jesusvilla.database.dao.ProfileDao
import com.jesusvilla.database.di.qualifiers.MovieDBRepositoryQualifier
import com.jesusvilla.database.di.qualifiers.MovieDaoQualifier
import com.jesusvilla.database.repository.MoviesDataRepository
import com.jesusvilla.database.repository.ProfileDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            dataBaseConfig.dataBaseName
        ).build()
    }

    @Provides
    @Singleton
    @ProfileDaoQualifier
    fun provideProfileDao(dataBase: DataBase) = dataBase.profileDao()

    @Provides
    @Singleton
    @ProfileDBRepositoryQualifier
    fun provideProfileRepository(@ProfileDaoQualifier profileDao: ProfileDao): ProfileDataRepository {
        return ProfileDataRepository(profileDao)
    }

    @Provides
    @Singleton
    @MovieDaoQualifier
    fun provideMovieDao(dataBase: DataBase) = dataBase.movieDao()

    @Provides
    @Singleton
    @MovieDBRepositoryQualifier
    fun provideMovieRepository(@MovieDaoQualifier movieDao: MoviesDao): MoviesDataRepository {
        return MoviesDataRepository(movieDao)
    }
}