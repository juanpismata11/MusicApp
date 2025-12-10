package com.example.musicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.musicapp.presentation.album.AlbumViewModel
import com.example.musicapp.presentation.album.AlbumState
import com.example.musicapp.screens.components.TopNotchShapeComposable


@Composable
fun AlbumScreen(
    navController: NavController,
    albumId: Int,
    viewModel: AlbumViewModel = viewModel()
) {
    val state by remember { viewModel.state }

    val backgroundColor = Color(0xFF151727)
    val cardColor = Color(0xFFE5E7EF)

    LaunchedEffect(albumId) {
        viewModel.loadAlbum(albumId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        // Back button
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .padding(top = 40.dp)
                .clickable { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .clip(
                    TopNotchShapeComposable(
                        cornerRadius = 16.dp,
                        cutoutRadius = 20.dp
                    )
                )
                .background(cardColor)
        ) {

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${state.error}")
                    }
                }

                else -> {
                    AlbumContent(state)
                }
            }
        }
    }
}

@Composable
private fun AlbumContent(state: AlbumState) {

    val album = state.album!!
    val artist = state.artist
    val songs = state.songs

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        // Top buttons (+ , download)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black)
            Icon(Icons.Default.Download, contentDescription = null, tint = Color.Black)
        }

        // Album cover
        AsyncImage(
            model = album.cover_url,
            contentDescription = album.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(vertical = 20.dp)
        )

        // Title + Artist + shuffle/play
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = album.title.uppercase(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = artist?.name?.uppercase() ?: "",
                    fontSize = 22.sp,
                    color = Color(0xFF62607C),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                )
            }
        }

        // Tracklist
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .weight(1f)
        ) {
            itemsIndexed(songs) { index, song ->
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = "${index + 1}  ${song.title}",
                        fontSize = 18.sp,
                        color = Color(0xFF62607C)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}