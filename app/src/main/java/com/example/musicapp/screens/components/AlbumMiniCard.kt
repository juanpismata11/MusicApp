package com.example.musicapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.musicapp.ui.theme.MusicAppTheme

val CardBackgroundColor = Color(0xFFE5E5E5)
val AlbumTitleColor = Color.Black
val ArtistColor = Color.Black

@Composable
fun AlbumMiniCard(
    albumId: Int,
    albumTitle: String,
    artistName: String,
    imageUrl: String,
    onClick: (Int) -> Unit
) {
    val cornerRadius = 18.dp

    Box(
        modifier = Modifier
            .width(170.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.18f),
                spotColor = Color.Black.copy(alpha = 0.22f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(CardBackgroundColor)
            .drawBehind {
                val strokeWidth = 18.dp.toPx()

                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.25f), Color.Transparent),
                        startY = 8f,
                        endY = strokeWidth
                    ),
                    size = size
                )

                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.25f), Color.Transparent),
                        startX = 0f,
                        endX = strokeWidth
                    ),
                    size = size
                )

                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.25f)),
                        startX = size.width - strokeWidth,
                        endX = size.width
                    ),
                    size = size
                )
            }
            .clickable { onClick(albumId) }
            .padding(14.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = imageUrl,
                contentDescription = albumTitle,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black)
            )

            Text(
                text = albumTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AlbumTitleColor,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = artistName,
                fontSize = 14.sp,
                color = ArtistColor.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE5E5E5)
@Composable
fun AlbumMiniCardPreview() {
    MusicAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            AlbumMiniCard(
                albumId = 1,
                albumTitle = "ARENA",
                artistName = "Pixel Grip",
                imageUrl = "https://via.placeholder.com/300.png",
                onClick = {}
            )
        }
    }

}
