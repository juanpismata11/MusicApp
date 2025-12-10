package com.example.musicapp.presentation.album

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Song
import com.example.musicapp.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlbumState(
    val isLoading: Boolean = false,
    val album: Album? = null,
    val artist: Artist? = null,
    val songs: List<Song> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repo: MusicRepository
) : ViewModel() {

    var state = mutableStateOf(AlbumState())
        private set

    fun loadAlbum(albumId: Int) {
        state.value = state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {

                val album = repo.getAlbumById(albumId)
                if (album == null) {
                    state.value = AlbumState(error = "Álbum no encontrado")
                    return@launch
                }

                val artist = repo.getArtistById(album.artistId ?: 0)
                val songs = repo.getSongsByAlbum(albumId)

                state.value = AlbumState(
                    album = album,
                    artist = artist,
                    songs = songs,
                    isLoading = false
                )

            } catch (e: Exception) {
                state.value = AlbumState(
                    error = e.message ?: "Error desconocido",
                    isLoading = false
                )
            }
        }
    }
}