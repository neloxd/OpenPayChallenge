package com.jesusvilla.profile.domain.mapper

import com.jesusvilla.database.entities.profile.KnownforEntity
import com.jesusvilla.database.entities.profile.ProfileEntity
import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.base.models.BaseDataResponse
import com.jesusvilla.profile.domain.model.KnownForUI
import com.jesusvilla.profile.domain.model.ProfileUI

fun BaseDataResponse.toProfileUI(): ProfileUI {
    return ProfileUI(
        name = name?:"",
        path = profilePath?:"",
        list = baseDataItemResponse.baseDataItemResponsetoKnownForUIList()?: listOf()
    )
}

fun List<BaseDataItemResponse>?.baseDataItemResponsetoKnownForUIList() = this?.map { it.toKnowForUI() }

fun BaseDataItemResponse.toKnowForUI(): KnownForUI {
    return KnownForUI(
        name = title?:"",
        originalName = originalTitle?:"",
        image = backdropPath?:"",
        overview = overview?:"",
    )
}

//region DB Mapper
fun KnownForUI.toKnowForEntity(): KnownforEntity {
    return KnownforEntity(
        id = 0,
        idProfileEntity = 0,
        name = name,
        originalName = originalName,
        image = image,
        overview = overview,
    )
}

fun KnownforEntity.toKnownForUI(): KnownForUI {
    return KnownForUI(
        name = name,
        originalName = originalName,
        image = image,
        overview = overview,
    )
}

fun List<KnownForUI>.toKnownForEntityList() = this.map { it.toKnowForEntity() }

fun List<KnownforEntity>.toKnownForUIList() = this.map { it.toKnownForUI() }

fun ProfileUI.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        idEntity = 0,
        name = name,
        path = path
    )
}

fun ProfileEntity.toProfileUI(knownForEntityList: List<KnownforEntity>): ProfileUI {
    return ProfileUI(
        name = name,
        path = path,
        list = knownForEntityList.toKnownForUIList()
    )
}
//endregion