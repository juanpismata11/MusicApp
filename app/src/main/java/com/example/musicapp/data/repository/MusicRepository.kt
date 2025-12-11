package com.example.musicapp.data.repository

import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.remote.data.LoginRequestDto
import com.example.musicapp.data.remote.data.SignupRequestDto
import com.example.musicapp.data.remote.data.UserDto

interface MusicRepository {

    suspend fun login(loginRequest: LoginRequestDto): Result<UserDto>
    suspend fun signup(signupRequest: SignupRequestDto): Result<UserDto>

    suspend fun getAlbumById(id: Int): AlbumDto?
    suspend fun getArtistById(id: Int): ArtistDto?
    suspend fun getSongsByAlbum(id: Int): List<SongDto>

    suspend fun getAllAlbums(): List<AlbumDto>
    suspend fun getAllArtists(): List<ArtistDto>
    suspend fun getAllSongs(): List<SongDto>
}
