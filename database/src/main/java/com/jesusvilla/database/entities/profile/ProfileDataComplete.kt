package com.jesusvilla.database.entities.profile

import androidx.room.Embedded
import androidx.room.Relation

class ProfileDataComplete (
    @Embedded val profileEntity: ProfileEntity,
    @Relation(
        parentColumn = "id_entity",
        entityColumn = "id_profile"
    )
    val knownforList: List<KnownforEntity> = emptyList(),
)