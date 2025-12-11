package com.example.musicapp.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.remote.MusicApi
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.SongDto
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

// Modelo que usarás en ExploreScreen (YA INCLUYE albumId)
data class SongWithAlbum(
    val id: Int,
    val title: String,
    val subtitle: String,
    val albumArtUrl: String?,
    val albumId: Int // <--- ESTO ES LO IMPORTANTE
)

class ExploreViewModel : ViewModel() {

    var songsWithAlbum by mutableStateOf<List<SongWithAlbum>>(emptyList())
        private set

    var artists by mutableStateOf<List<com.example.musicapp.data.remote.data.ArtistDto>>(emptyList())
        private set

    var albumOfTheWeek by mutableStateOf<AlbumDto?>(null)
        private set

    var loading by mutableStateOf(true)
        private set

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val api: MusicApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://music-api-u681.onrender.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicApi::class.java)
    }

    init {
        loadExploreData()
    }

    private fun loadExploreData() {
        viewModelScope.launch {
            loading = true
            try {
                val songs = api.getSongs()
                val albums = api.getAlbums()
                val artistsApi = api.getArtists()

                artists = artistsApi

                songsWithAlbum = songs.map { song ->
                    val album = albums.firstOrNull { it.id == song.albumId }

                    SongWithAlbum(
                        id = song.id,
                        title = song.title,
                        subtitle = album?.title ?: "Unknown Album",
                        albumArtUrl = album?.coverUrl,
                        albumId = song.albumId ?: 0 // <--- AQUÍ SE GUARDA albumId REAL
                    )
                }

                albumOfTheWeek = albums.randomOrNull()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            loading = false
        }
    }
}