package com.jesusvilla.database.repository

import androidx.annotation.WorkerThread
import com.jesusvilla.database.di.qualifiers.ProfileDaoQualifier
import com.jesusvilla.database.dao.ProfileDao
import com.jesusvilla.database.entities.profile.KnownforEntity
import com.jesusvilla.database.entities.profile.ProfileDataComplete
import com.jesusvilla.database.entities.profile.ProfileEntity
import com.jesusvilla.database.resources.DataBaseResource
import javax.inject.Inject

class ProfileDataRepository @Inject constructor(
    @ProfileDaoQualifier private val profileDao: ProfileDao) {

    @WorkerThread
    suspend fun getLastProfileCoroutine(): DataBaseResource<ProfileDataComplete?> {
        return try {
            DataBaseResource.Success(profileDao.getLastProfile())
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun deleteProfileData(): DataBaseResource<Boolean> {
        return try {
            profileDao.deleteProfile()
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun insertProfileWithKnownFor(
        profileEntity: ProfileEntity,
        knownforEntity: List<KnownforEntity>
    ) {
        deleteProfileData()
        val profileId = profileDao.insertNewProfile(profileEntity)
        knownforEntity.forEach { it.idProfileEntity = profileId }
        profileDao.insertKnownForList(knownforEntity)
    }
}
