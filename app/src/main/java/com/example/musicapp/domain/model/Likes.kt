package com.example.musicapp.domain.model


data class LikedSong(
    val id: Int,
    val title: String,
    val audio_path: String,
    val artist_name: String? = null
)


data class LikedArtist(
    val id: Int,
    val name: String,
    val pic_url: String?
)


data class LikedAlbum(
    val id: Int,
    val title: String,
    val cover_url: String?,
    val artist_name: String? = null
)

