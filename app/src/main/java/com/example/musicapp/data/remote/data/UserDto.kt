package com.example.musicapp.data.remote.data

data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val favorites: FavoritesDto
)