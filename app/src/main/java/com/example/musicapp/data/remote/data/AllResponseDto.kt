package com.example.musicapp.data.remote.dto

data class AllResponseDto(
    val artists: List<ArtistDto>,
    val albums: List<AlbumDto>,
    val songs: List<SongDto>
)