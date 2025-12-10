package com.example.musicapp.data.remote.data


data class LikeDto(
    val user_id: Int,
    val item_type: String, // Ejemplo: "song", "artist", "album"
    val item_id: Int
)