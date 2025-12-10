package com.example.musicapp.data.services

import com.example.musicapp.data.remote.dto.ArtistDto
import retrofit2.http.GET

interface ArtistService {

    @GET("artists")
    suspend fun getAllArtists(): List<ArtistDto>

}