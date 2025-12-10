package com.example.musicapp.domain.model

data class Album(
    val id: Int,
    val title: String,
    val description: String,
    val coverUrl: String,
    val artistId: Int,
    val category: String
)