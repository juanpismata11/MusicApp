package com.example.musicapp.data.remote.dto

import com.example.musicapp.domain.model.*

fun ArtistDto.toDomain(): Artist =
    Artist(
        id = id,
        name = name,
        bio = bio,
        country = country
    )

fun AlbumDto.toDomain(): Album =
    Album(
        id = id,
        title = title,
        description = description,
        coverUrl = cover_url,
        artistId = artist_id
    )

fun SongDto.toDomain(baseUrl: String): Song {
    // audio_path viene como "audio/archivo.mp3"
    // La API sirve el audio en /media
    val fullUrl = audio_path?.let { "$baseUrl$it" } // luego pasamos "http://10.0.2.2:8000/media/"
    return Song(
        id = id,
        title = title,
        duration = duration,
        albumId = album_id,
        artistId = artist_id,
        audioUrl = fullUrl
    )
}

fun FavoritesDto.toDomain(): Favorites =
    Favorites(
        artists = artists,
        albums = albums,
        songs = songs
    )

fun UserDto.toDomain(): User =
    User(
        id = id,
        username = username,
        email = email,
        favorites = favorites.toDomain()
    )