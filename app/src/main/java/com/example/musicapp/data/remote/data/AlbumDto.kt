package com.example.musicapp.data.remote.data

data class AlbumDto(
    val id: Int,
    val title: String,
    val description: String?,
    val category: String,
    val cover_url: String?,
    val artist_id: Int
)