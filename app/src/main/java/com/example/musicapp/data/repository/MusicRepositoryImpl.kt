package com.example.musicapp.data.repository

import com.example.musicapp.data.remote.MusicApi
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.remote.data.LoginRequestDto
import com.example.musicapp.data.remote.data.SignupRequestDto
import com.example.musicapp.data.remote.data.UserDto
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val api: MusicApi
) : MusicRepository {


    override suspend fun login(loginRequest: LoginRequestDto): Result<UserDto> {
        return try {
            val response = api.login(loginRequest)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun signup(signupRequest: SignupRequestDto): Result<UserDto> {
        return try {
            val response = api.signup(signupRequest)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAlbumById(id: Int): AlbumDto? {
        return try {
            api.getAlbums().find { it.id == id }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getArtistById(id: Int): ArtistDto? {
        return try {
            api.getArtists().find { it.id == id }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getSongsByAlbum(id: Int): List<SongDto> {
        return try {
            api.getSongs().filter { it.albumId == id }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getAllAlbums(): List<AlbumDto> {
        return try {
            api.getAlbums()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getAllArtists(): List<ArtistDto> {
        return try {
            api.getArtists()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getAllSongs(): List<SongDto> {
        return try {
            api.getSongs()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
