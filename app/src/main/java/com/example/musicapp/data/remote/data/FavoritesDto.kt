package com.example.musicapp.data.remote.dto

data class FavoritesDto(
    val artists: List<Int> = emptyList(),
    val albums: List<Int> = emptyList(),
    val songs: List<Int> = emptyList()
)