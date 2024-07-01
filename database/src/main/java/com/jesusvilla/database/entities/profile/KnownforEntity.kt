package com.jesusvilla.database.entities.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "knownfor",
    foreignKeys = [ForeignKey(entity = ProfileEntity::class,
        parentColumns = ["id_entity"],
        childColumns = ["id_profile"],
        onDelete = CASCADE)]
)

data class KnownforEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo("id_profile")
    var idProfileEntity: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("originalName")
    val originalName: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("image")
    val image: String,
)