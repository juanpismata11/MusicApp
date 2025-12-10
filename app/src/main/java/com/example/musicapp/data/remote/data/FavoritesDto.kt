package com.example.musicapp.data.remote.data

data class FavoritesDto(
    val artists: List<Int> = emptyList(),
    val albums: List<Int> = emptyList(),
    val songs: List<Int> = emptyList()
)