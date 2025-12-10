package com.example.musicapp.domain.model

data class Song(
    val id: Int,
    val title: String,
    val duration: String?,
    val albumId: Int?,
    val artistId: Int?,
    val audioUrl: String?   // luego construiremos la URL completa
)