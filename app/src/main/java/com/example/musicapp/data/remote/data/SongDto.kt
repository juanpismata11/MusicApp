package com.example.musicapp.data.remote.data

data class SongDto(
    val id: Int,
    val title: String,
    val duration: String?,
    val album_id: Int?,
    val artist_id: Int?,
    val audio_path: String?
)