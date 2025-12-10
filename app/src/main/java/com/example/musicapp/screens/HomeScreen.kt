package com.example.musicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.musicapp.data.remote.dto.ArtistDto
import com.example.musicapp.data.services.ArtistService
import com.example.musicapp.screens.components.CardsHomeScreen
import com.example.musicapp.screens.components.TopNotchShapeComposable
import com.example.musicapp.ui.theme.Routes
import com.example.musicapp.ui.theme.gray50
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java
import kotlin.reflect.KClass


@Composable
fun HomeScreen(navController: NavController) {
    val backgroundColor = Color(0xFF151727)
    val cardColor = Color(0xFFE5E7EF)

    var text by remember { mutableStateOf("") }

    var artists by remember {
        mutableStateOf(listOf<ArtistDto>())
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    var disabled by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MusicApp",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,

                )

            }
        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 80.dp)
                .clip(TopNotchShapeComposable(
                    cornerRadius = 16.dp,
                    cutoutRadius = 20.dp
                ))
                .background(cardColor)
        ) {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
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
                        text = "Juan Pablo",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp)

                    )
                }

                Text(
                    text = "Musica en todos lados",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Busca artistas", color = gray50) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = gray50) },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 5.dp)
                        .shadow(8.dp, RoundedCornerShape(20.dp))
                )

                LaunchedEffect(true) {
                    try{
                        val retrofit = Retrofit
                            .Builder()
                            .baseUrl("https://music-api-u681.onrender.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                        val service = retrofit.create(ArtistService::class.java)
                        val result = async(Dispatchers.IO) {
                            service.getAllArtists()
                        }
                        Log.i("HomeScreen","${result.await()}")
                        artists = result.await()
                        loading = false
                    } catch(e: Exception){
                        Log.i("HomeScreen",e.toString())
                    }
                }

            if(loading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cardColor)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            } else {
                if (error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Error",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = error ?: "",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            androidx.compose.material3.Button(
                                onClick = {
                                    loading = true
                                    error = null
                                    disabled = true
                                },
                                enabled = !disabled,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(if (disabled) "Cargando..." else "Reintentar")
                            }
                        }
                    }
                }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp)
                            .background(cardColor)

                    ) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(artists) { artist ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AsyncImage(
                                        model = artist.artist_pic,
                                        contentDescription = artist.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .background(Color.Gray),
                                        error = null
                                    )
                                    Text(
                                        text = artist.name,
                                        color = backgroundColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top = 10.dp)
                                    )
                                }
                            }
                        }


                        Text(
                            text = "Deep dive into",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        Column(Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier.
                                fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                CardsHomeScreen(title = "Mi Libreria") //esto seria lo de favoritos
                                CardsHomeScreen(title = "No se")
                            }


                            Row {
                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .clickable {
                                            navController.navigate(Routes.Explore)
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.BottomStart)
                                                .padding(start = 12.dp, bottom = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Descubrimientos Musicales",
                                                color = Color.White,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = "Ir a Descubrimientos",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .offset(x = (-10).dp)
                                            )
                                        }
                                    }

                            }
                        }




                    }




                }
            }





            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
