package com.example.musicapp.domain.model

data class Favorites(
    val artists: List<Int> = emptyList(),
    val albums: List<Int> = emptyList(),
    val songs: List<Int> = emptyList()
)