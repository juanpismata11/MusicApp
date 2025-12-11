package com.example.musicapp.data.remote.data

data class LikesGroupedDto(
    val songs: List<SongDto> = emptyList(),
    val artists: List<ArtistDto> = emptyList(),
    val albums: List<AlbumDto> = emptyList()
)