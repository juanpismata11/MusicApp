package com.example.musicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import android.util.Log
import com.example.musicapp.data.remote.MusicApi
import com.example.musicapp.data.remote.data.AlbumDto
import com.example.musicapp.data.remote.data.ArtistDto
import com.example.musicapp.data.remote.data.SongDto
import com.example.musicapp.screens.components.TopNotchShapeComposable
import com.example.musicapp.ui.theme.gray50
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Colores de referencia
val BackgroundColor = Color(0xFF151727)
val CardColor = Color(0xFFE5E7EF)


// =========================================================================
// FUNCIÓN PRINCIPAL (MANEJA ESTADO Y LLAMADA A LA API)
// =========================================================================

@Composable
fun LibraryScreen(
    navController: NavController
) {
    // --- ESTADOS LOCALES PARA LOS DATOS ---
    var likedSongs by remember { mutableStateOf<List<SongDto>>(emptyList()) }
    var likedArtists by remember { mutableStateOf<List<ArtistDto>>(emptyList()) }
    var likedAlbums by remember { mutableStateOf<List<AlbumDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    val currentUserId = 1
    val currentUsername = "Juan Pablo"

    // --- LÓGICA DE CARGA DIRECTA CON RETROFIT ---
    LaunchedEffect(Unit) {
        isLoading = true
        error = null
        try {
            val retrofit = Retrofit
                .Builder()
                .baseUrl("https://music-api-u681.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(MusicApi::class.java)

            val result = async(Dispatchers.IO) {
                api.getUserLikes(currentUserId)
            }

            val likesGrouped = result.await()

            likedSongs = likesGrouped.songs
            likedArtists = likesGrouped.artists
            likedAlbums = likesGrouped.albums

        } catch (e: Exception) {
            Log.e("LibraryScreen", "Error al cargar likes: ${e.message}", e)
            error = "Error al conectar o cargar datos: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // --- LLAMADA AL LAYOUT ---
    LibraryLayout(
        currentUsername = currentUsername,
        likedSongs = likedSongs,
        likedArtists = likedArtists,
        likedAlbums = likedAlbums,
        isLoading = isLoading,
        error = error,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { selectedTabIndex = it }
    )
}


// =========================================================================
// FUNCIÓN DE LAYOUT (SOLO UI - SEGURA PARA PREVIEW)
// =========================================================================

@Composable
fun LibraryLayout(
    currentUsername: String,
    likedSongs: List<SongDto>,
    likedArtists: List<ArtistDto>,
    likedAlbums: List<AlbumDto>,
    isLoading: Boolean,
    error: String?,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Canciones", "Artistas", "Álbumes")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        // --- CABECERA SUPERIOR (Título de la Pantalla) ---
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mi Biblioteca",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        // --- CUERPO PRINCIPAL (Fondo Blanco Recortado) ---
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 80.dp)
                .clip(TopNotchShapeComposable(
                    cornerRadius = 16.dp,
                    cutoutRadius = 20.dp
                ))
                .background(CardColor)
        ) {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                // 1. Información del Perfil
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Black, shape = CircleShape)
                    )

                    Text(
                        text = currentUsername,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp),
                        color = BackgroundColor
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // 2. PESTAÑAS (ScrollableTabRow)
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = BackgroundColor,
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { onTabSelected(index) }, // Usa el callback
                            text = {
                                Text(
                                    title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (selectedTabIndex == index) BackgroundColor else gray50
                                )
                            }
                        )
                    }
                }
                Divider(color = gray50.copy(alpha = 0.5f), thickness = 1.dp)

                // 3. CONTENIDO (Listas de Likes)
                Box(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BackgroundColor)
                    } else if (error != null) {
                        Text("Error: $error", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                    } else {
                        // Mostrar la lista correspondiente a la pestaña seleccionada
                        when (selectedTabIndex) {
                            0 -> LikedSongsList(likedSongs, BackgroundColor, gray50)
                            1 -> LikedArtistsList(likedArtists, BackgroundColor, gray50)
                            2 -> LikedAlbumsList(likedAlbums, BackgroundColor, gray50)
                        }

                        // Mensaje si la lista está vacía
                        val currentListIsEmpty = when (selectedTabIndex) {
                            0 -> likedSongs.isEmpty()
                            1 -> likedArtists.isEmpty()
                            else -> likedAlbums.isEmpty()
                        }

                        if (currentListIsEmpty) {
                            Text("No hay ${tabs[selectedTabIndex].lowercase()} favoritos.", color = gray50, modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}


// =========================================================================
// COMPONENTES DE LISTA Y PREVIEW
// =========================================================================

// --- Componentes Reutilizables de Listas (Se mantienen igual) ---

@Composable
fun LikedSongsList(songs: List<SongDto>, mainColor: Color, secondaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(songs) { song ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF3B5998), shape = RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(song.title, fontWeight = FontWeight.SemiBold, color = mainColor)
                    Text("Álbum: ${song.album_id}", fontSize = 14.sp, color = secondaryColor)
                }
            }
            Divider(color = secondaryColor.copy(alpha = 0.3f))
        }
    }
}

@Composable
fun LikedArtistsList(artists: List<ArtistDto>, mainColor: Color, secondaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(artists) { artist ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = artist.artist_pic,
                    contentDescription = artist.name,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(secondaryColor),
                    error = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(artist.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = mainColor)
            }
            Divider(color = secondaryColor.copy(alpha = 0.3f))
        }
    }
}

@Composable
fun LikedAlbumsList(albums: List<AlbumDto>, mainColor: Color, secondaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(albums) { album ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = album.cover_url,
                    contentDescription = album.title,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(secondaryColor),
                    error = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(album.title, fontWeight = FontWeight.SemiBold, color = mainColor)
                    Text("Artista ID: ${album.artist_id}", fontSize = 14.sp, color = secondaryColor)
                }
            }
            Divider(color = secondaryColor.copy(alpha = 0.3f))
        }
    }
}

// --- DATOS MOCK PARA EL PREVIEW ---
val mockSong = SongDto(id = 1, title = "Mock Song", duration = "3:00", album_id = 1, artist_id = 1, audio_path = "")
val mockArtist = ArtistDto(id = 1, name = "Mock Artist", bio = "", country = "", artist_pic = "")
val mockAlbum = AlbumDto(id = 1, title = "Mock Album", description = "", category = "Pop", cover_url = "", artist_id = 1)


// --- PREVIEW (¡El que SÍ funcionará!) ---
@Preview(showBackground = true)
@Composable
fun LibraryLayoutPreview() {
    LibraryLayout(
        currentUsername = "Juan Pablo (Preview)",
        likedSongs = listOf(mockSong, mockSong.copy(title = "Otra canción")),
        likedArtists = listOf(mockArtist, mockArtist.copy(name = "Artista 2")),
        likedAlbums = listOf(mockAlbum),
        isLoading = false,
        error = null,
        selectedTabIndex = 0,
        onTabSelected = {}
    )
}