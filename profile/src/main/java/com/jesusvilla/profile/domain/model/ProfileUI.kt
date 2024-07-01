package com.jesusvilla.profile.domain.model

data class ProfileUI(
    val name: String,
    val path: String,
    val list: List<KnownForUI> = listOf()
)

data class KnownForUI(
    val name: String,
    val originalName: String,
    val overview: String,
    val image: String,
)