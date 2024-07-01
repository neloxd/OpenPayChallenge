package com.jesusvilla.movies.domain.mapper

import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.database.entities.movie.MovieEntity

fun List<BaseDataItemResponse>?.baseDataItemResponseToMovieBaseUIList() = this?.map { it.baseToMovieBaseUI() }

fun BaseDataItemResponse.baseToMovieBaseUI(): BaseUI {
    return BaseUI(
        name = title?:"",
        originalName = originalTitle?:"",
        image = backdropPath?:"",
        overview = overview?:"",
        voteAverage = voteAverage?:0.0,
        voteCount = voteCount?:0,
    )
}

//region DB Mapper
fun BaseUI.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = 0,
        name = name,
        originalName = originalName,
        image = image,
        overview = overview,
        voteCount = voteCount,
        voteAverage = voteAverage
    )
}

fun MovieEntity.toMovieBaseUI(): BaseUI {
    return BaseUI(
        name = name,
        originalName = originalName,
        image = image,
        overview = overview,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun List<BaseUI>.toMovieEntityList() = this.map { it.toMovieEntity() }

fun List<MovieEntity>.toMovieBaseUIList() = this.map { it.toMovieBaseUI() }
//endregion