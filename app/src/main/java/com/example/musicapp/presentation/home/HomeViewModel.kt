package com.example.musicapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.local.SessionManager
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = false,
    val userName: String = "Usuario",
    val artists: List<ArtistDto> = emptyList(),
    val albums: List<AlbumDto> = emptyList(),
    val songs: List<SongDto> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            sessionManager.userName.collect { name ->
                if (name != null) {
                    _state.update { it.copy(userName = name) }
                }
            }
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val artistsDeferred = async { repository.getArtists() }
                val albumsDeferred = async { repository.getAlbums() }
                val songsDeferred = async { repository.getSongs() }

                val artistsResult = artistsDeferred.await()
                val albumsResult = albumsDeferred.await()
                val songsResult = songsDeferred.await()

                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        artists = artistsResult.getOrElse { emptyList() },
                        albums = albumsResult.getOrElse { emptyList() },
                        songs = songsResult.getOrElse { emptyList() }
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Error: ${e.message}")
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }
}
