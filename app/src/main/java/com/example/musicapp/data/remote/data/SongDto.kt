package com.example.musicapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class SongDto(
    val id: Int,
    val title: String,
    val duration: String?,
    @SerializedName("album_id") val albumId: Int?,
    @SerializedName("artist_id") val artistId: Int?,
    @SerializedName("audio_path") val audioUrl: String?
)