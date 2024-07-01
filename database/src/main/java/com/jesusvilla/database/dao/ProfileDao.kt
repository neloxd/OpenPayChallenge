package com.jesusvilla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jesusvilla.database.entities.profile.KnownforEntity
import com.jesusvilla.database.entities.profile.ProfileDataComplete
import com.jesusvilla.database.entities.profile.ProfileEntity

@Dao
interface ProfileDao {

    @Transaction
    @Query("SELECT * FROM PROFILE ORDER BY id_entity LIMIT 1")
    suspend fun getLastProfile(): ProfileDataComplete?

    @Query("SELECT * FROM KNOWNFOR WHERE id_profile = :idProfile")
    fun getKnownForListByProfile(idProfile: Long): List<KnownforEntity>

    @Query("DELETE FROM PROFILE")
    suspend fun deleteProfile()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewProfile(profile: ProfileEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnownForList(tags: List<KnownforEntity>): List<Long>
}
