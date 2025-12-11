package com.example.musicapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    val id: Int,
    val title: String,
    val description: String?,
    val category: String,
    @SerializedName("cover_url") val coverUrl: String?,
    @SerializedName("artist_id") val artistId: Int
)