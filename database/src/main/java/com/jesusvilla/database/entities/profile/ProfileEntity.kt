package com.jesusvilla.database.entities.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_entity")
    var idEntity: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "path")
    val path: String
)
