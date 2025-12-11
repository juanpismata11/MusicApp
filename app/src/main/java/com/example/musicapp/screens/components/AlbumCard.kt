import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

val CardBackgroundColor = Color(0xFFE5E5E5)
val AlbumTitleColor = Color.Black
val ArtistColor = Color.Black
val HeaderColor = Color(0xFF4A4A4A)

@Composable
fun AlbumCard(
    albumTitle: String,
    artistName: String,
    albumImageUrl: String? = null // URL del álbum
) {
    val DefaultAlbumImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqjGNmx3-Kx3DeJiNLf23JCEaA-jw7HQOKSw&s"

    val cornerRadius = 20.dp

    Box(
        modifier = Modifier
            .width(340.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.18f),
                spotColor = Color.Black.copy(alpha = 0.22f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(CardBackgroundColor)
            .drawBehind {
                val strokeWidth = 25.dp.toPx()

                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.25f), Color.Transparent),
                        startY = 10f,
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
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Album of the Week",
                    color = HeaderColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = albumTitle.uppercase(),
                    color = AlbumTitleColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 40.sp
                )

                Text(
                    text = artistName,
                    color = ArtistColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            AsyncImage(
                model = albumImageUrl ?: DefaultAlbumImage,
                contentDescription = "$albumTitle cover",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}