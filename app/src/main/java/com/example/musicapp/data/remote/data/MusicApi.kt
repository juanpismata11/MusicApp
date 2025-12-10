package com.example.musicapp.data.remote

import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import retrofit2.http.GET

interface MusicApi {

    @GET("/albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("/artists")
    suspend fun getArtists(): List<ArtistDto>

    @GET("/songs")
    suspend fun getSongs(): List<SongDto>
}