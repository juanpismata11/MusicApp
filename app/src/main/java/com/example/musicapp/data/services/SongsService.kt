package com.example.musicapp.data.services

import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import retrofit2.http.GET

interface SongsService {
    @GET("songs/songs")
    suspend fun getAllSongs(): List<SongDto>
}