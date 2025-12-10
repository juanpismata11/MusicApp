package com.example.musicapp.data.remote.mappers

import com.example.musicapp.data.remote.dto.AlbumDto
import com.example.musicapp.data.remote.dto.ArtistDto
import com.example.musicapp.data.remote.dto.SongDto
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Song

// ---------------- ARTIST ----------------

fun ArtistDto.toArtist(): Artist {
    return Artist(
        id = id,
        name = name,
        bio = bio ?: "",
        country = country ?: "",
        artistPic = artist_pic ?: ""
    )
}

// ---------------- ALBUM ----------------

fun AlbumDto.toAlbum(): Album {
    return Album(
        id = id,
        title = title,
        description = description ?: "",
        coverUrl = cover_url ?: "",
        artistId = artist_id,
        category = category
    )
}

// ---------------- SONG ----------------

fun SongDto.toSong(): Song {
    return Song(
        id = id,
        title = title,
        duration = duration ?: "",
        albumId = album_id,
        artistId = artist_id,
        audioUrl = audio_path ?: ""
    )
}