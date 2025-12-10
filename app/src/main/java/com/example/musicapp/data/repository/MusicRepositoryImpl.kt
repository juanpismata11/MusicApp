package com.example.musicapp.data.repository

import com.example.musicapp.data.remote.MusicApi
import com.example.musicapp.data.remote.mappers.toAlbum
import com.example.musicapp.data.remote.mappers.toArtist
import com.example.musicapp.data.remote.mappers.toSong
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Song
import javax.inject.Inject


class MusicRepositoryImpl @Inject constructor(
    private val api: MusicApi
) : MusicRepository {

    override suspend fun getAlbumById(id: Int): Album? {
        return api.getAlbums()
            .map { it.toAlbum() }
            .firstOrNull { it.id == id }
    }

    override suspend fun getArtistById(id: Int): Artist? {
        return api.getArtists()
            .map { it.toArtist() }
            .firstOrNull { it.id == id }
    }

    override suspend fun getSongsByAlbum(id: Int): List<Song> {
        return api.getSongs()
            .map { it.toSong() }
            .filter { it.albumId == id }
    }

    override suspend fun getAllAlbums(): List<Album> {
        return api.getAlbums().map { it.toAlbum() }
    }

    override suspend fun getAllArtists(): List<Artist> {
        return api.getArtists().map { it.toArtist() }
    }

    override suspend fun getAllSongs(): List<Song> {
        return api.getSongs().map { it.toSong() }
    }
}