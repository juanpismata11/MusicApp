package com.example.musicapp.data.remote


import com.example.musicapp.data.remote.dto.*
import retrofit2.http.*


interface MusicApi {

    // ARTISTS
    @GET("artists")
    suspend fun getArtists(): List<ArtistDto>

    @GET("artists/{id}")
    suspend fun getArtist(
        @Path("id") id: Int
    ): ArtistDto

    // ALBUMS
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("albums/{id}")
    suspend fun getAlbum(
        @Path("id") id: Int
    ): AlbumDto

    // SONGS
    @GET("songs")
    suspend fun getSongs(): List<SongDto>

    @GET("songs/{id}")
    suspend fun getSong(
        @Path("id") id: Int
    ): SongDto

    // USERS
    @POST("users")
    suspend fun register(
        @Body body: UserCreateDto
    ): UserDto

    @POST("login")
    suspend fun login(
        @Body body: LoginRequestDto
    ): UserDto

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: Int
    ): UserDto

    @POST("users/{id}/favorites")
    suspend fun updateFavorites(
        @Path("id") userId: Int,
        @Body favorites: FavoritesDto
    ): Map<String, Boolean> // {"updated": true}

    // ALL
    @GET("all")
    suspend fun getAll(): AllResponseDto
}