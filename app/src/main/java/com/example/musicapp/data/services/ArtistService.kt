package com.example.musicapp.data.services

import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistService {

    @GET("artists")
    suspend fun getAllArtists(): List<ArtistDto>

    @GET("artist/{id}")
    suspend fun getArtistById(@Path("id") id: Int): ArtistDto

}