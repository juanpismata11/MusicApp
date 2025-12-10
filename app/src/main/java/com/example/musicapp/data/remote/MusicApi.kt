package com.example.musicapp.data.remote

import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.LoginRequestDto
import com.example.musicapp.data.remote.data.SignupRequestDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.remote.data.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MusicApi {

    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("artists")
    suspend fun getArtists(): List<ArtistDto>

    @GET("songs")
    suspend fun getSongs(): List<SongDto>

    @POST("users")
    suspend fun signup(@Body request: SignupRequestDto): UserDto

    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): UserDto
}
