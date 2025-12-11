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

    override suspend fun getAlbums(): Result<List<AlbumDto>> {
        return try {
            val result = api.getAlbums()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArtists(): Result<List<ArtistDto>> {
        return try {
            val result = api.getArtists()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSongs(): Result<List<SongDto>> {
        return try {
            val result = api.getSongs()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
