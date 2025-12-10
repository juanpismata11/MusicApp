package com.example.musicapp.data.repository

import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Song

interface MusicRepository {

    suspend fun getAlbumById(id: Int): Album?
    suspend fun getArtistById(id: Int): Artist?
    suspend fun getSongsByAlbum(id: Int): List<Song>

    suspend fun getAllAlbums(): List<Album>
    suspend fun getAllArtists(): List<Artist>
    suspend fun getAllSongs(): List<Song>
}