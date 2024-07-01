package com.jesusvilla.profile.di

import com.jesusvilla.core.network.connection.RetrofitApi
import com.jesusvilla.profile.data.repository.ProfileRepository
import com.jesusvilla.profile.data.repository.ProfileRepositoryImpl
import com.jesusvilla.profile.data.service.ProfileAPI
import com.jesusvilla.profile.domain.useCase.GetPopularProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {
    @Provides
    @ViewModelScoped
    @ProfileQualifier
    fun provideProfileApi(@RetrofitApi retrofit: Retrofit): ProfileAPI {
        return retrofit.create(ProfileAPI::class.java)
    }

    @Provides
    @ViewModelScoped
    @ProfileRepositoryQualifier
    fun provideProfileRepository(@ProfileQualifier profileAPI: ProfileAPI): ProfileRepository {
        return ProfileRepositoryImpl(profileAPI)
    }

    @Provides
    @ViewModelScoped
    @GetPopularProfileUseCaseQualifier
    fun provideGetPopularProfileUseCase(@ProfileRepositoryQualifier profileRepository: ProfileRepository): GetPopularProfileUseCase {
        return GetPopularProfileUseCase(profileRepository)
    }

}