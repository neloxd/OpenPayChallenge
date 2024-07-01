package com.jesusvilla.database.entities.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("originalName")
    val originalName: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("voteAverage")
    val voteAverage: Double,
    @ColumnInfo("voteCount")
    val voteCount: Int
)