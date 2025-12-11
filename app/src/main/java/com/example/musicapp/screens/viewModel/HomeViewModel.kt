package com.example.musicapp.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.services.AlbumService
import com.example.musicapp.data.services.SongsService
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.musicapp.data.services.ArtistService

data class SongCardData(
    val albumArtUrl: String,
    val title: String,
    val subtitle: String
)

class ExploreViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://music-api-u681.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val albumService = retrofit.create(AlbumService::class.java)
    private val songsService = retrofit.create(SongsService::class.java)
    private val artistsService = retrofit.create(ArtistService::class.java)

    var songsWithAlbum by mutableStateOf<List<SongCardData>>(emptyList())
        private set

    var albumOfTheWeek by mutableStateOf<AlbumDto?>(null)
        private set

    var loading by mutableStateOf(true)
        private set

    var artists by mutableStateOf<List<ArtistDto>>(emptyList())
        private set

    init {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch {
        try {
            // Llamadas paralelas
            val albumsDeferred = async { albumService.getAllAlbums() }
            val songsDeferred = async { songsService.getAllSongs() }
            val artistsDeferred = async { artistsService.getAllArtists() }

            val albums = albumsDeferred.await()
            val songs = songsDeferred.await()
            artists = artistsDeferred.await()

            // Álbum destacado
            albumOfTheWeek = albums.find { it.id == 1 }

            // Mapeo canciones con info de álbum y artista
            songsWithAlbum = songs.map { song ->
                val album = albums.find { it.id == song.albumId }
                val albumArtist = artists.find { it.id == album?.artistId }
                SongCardData(
                    albumArtUrl = album?.coverUrl ?: "https://via.placeholder.com/300.png",
                    title = song.title,
                    subtitle = "${album?.title ?: "Unknown Album"} - ${albumArtist?.name ?: "Unknown Artist"}"
                )
            }

        } catch (e: Exception) {
            Log.e("ExploreViewModel", e.toString())
        } finally {
            loading = false
        }
    }
}
