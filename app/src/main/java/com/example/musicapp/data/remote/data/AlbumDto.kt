package com.example.musicapp.data.remote.dto

data class AlbumDto(
    val id: Int,
    val title: String,
    val description: String?,
    val cover_url: String?,
    val artist_id: Int?
)