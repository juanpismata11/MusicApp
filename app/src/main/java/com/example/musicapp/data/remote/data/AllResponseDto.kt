package com.example.musicapp.data.remote.data

data class AllResponseDto(
    val artists: List<ArtistDto>,
    val albums: List<AlbumDto>,
    val songs: List<SongDto>
)