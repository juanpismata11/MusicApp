package com.example.musicapp.presentation.album

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.data.repository.MusicRepository // Asegúrate de que apunte a la Interfaz, no a la clase Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlbumState(
    val isLoading: Boolean = false,
    val album: AlbumDto? = null,
    val artist: ArtistDto? = null,
    val songs: List<SongDto> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repo: MusicRepository
) : ViewModel() {

    var state = mutableStateOf(AlbumState())
        private set

    fun loadAlbum(albumId: Int) {
        state.value = state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val album = repo.getAlbumById(albumId)

                if (album == null) {
                    state.value = AlbumState(error = "Álbum no encontrado", isLoading = false)
                    return@launch
                }

                val artist = repo.getArtistById(album.artist_id)

                val songs = repo.getSongsByAlbum(albumId)

                state.value = AlbumState(
                    album = album,
                    artist = artist,
                    songs = songs,
                    isLoading = false
                )

            } catch (e: Exception) {
                e.printStackTrace()
                state.value = AlbumState(
                    error = e.message ?: "Error desconocido al cargar el álbum",
                    isLoading = false
                )
            }
        }
    }
}
