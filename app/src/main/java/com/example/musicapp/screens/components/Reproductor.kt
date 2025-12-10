import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.musicapp.ui.theme.BorderColor
import com.example.musicapp.ui.theme.DotColor
import com.example.musicapp.ui.theme.PlayerCasingColor
import com.example.musicapp.ui.theme.PlayerDisplayColor
import com.example.musicapp.ui.theme.SongTitleBarColor
import com.example.musicapp.ui.theme.TextColorSecondary

@Composable
fun Reproductor() {
    Box(
        modifier = Modifier
            .width(340.dp)
            .height(115.dp)
            .padding(4.dp)
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 60.dp)
                .offset(y = 9.dp)
                .zIndex(0f)
        ) {
            VolumeButton()
            Spacer(modifier = Modifier.width(4.dp))
            VolumeButton()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(36.dp))
                .background(BorderColor)
                .padding(4.dp)
                .zIndex(1f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(34.dp))
                    .background(PlayerCasingColor)
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerControls(modifier = Modifier.weight(1.1f))

                PlayerScreen(modifier = Modifier.weight(2.4f))
            }
        }
    }
}

@Composable
fun VolumeButton() {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(18.dp)
            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
            .background(BorderColor)
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(BorderColor)
        )
    }
}

@Composable
fun PlayerControls(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black,
                modifier = Modifier
                    .size(42.dp)
                    .offset(x = 0.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        ) {
            Box(modifier = Modifier.offset(x = 0.dp)) {
                RepeatableDot(size = 8.dp)
            }

            Box(modifier = Modifier.offset(x = -7.dp)) {
                RepeatableDot(size = 8.dp)
            }

            Box(modifier = Modifier.offset(x = -10.dp)) {
                RepeatableDot(size = 8.dp)
            }

            Box(modifier = Modifier.offset(x = -10.dp)) {
                RepeatableDot(size = 8.dp)
            }

            Box(modifier = Modifier.offset(x = -7.dp)) {
                RepeatableDot(size = 8.dp)
            }

            Box(modifier = Modifier.offset(x = 0.dp)) {
                RepeatableDot(size = 8.dp)
            }
        }
    }
}

@Composable
fun RepeatableDot(size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(DotColor)
    )
}

@Composable
fun PlayerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(36.dp))
            .background(PlayerDisplayColor)
            .padding(vertical = 8.dp, horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "DIGITAL MP3 PLAYER",
            color = TextColorSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(SongTitleBarColor)
                .padding(vertical = 6.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Digital Love - Daft Punk",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }

        Text(
            text = "P3 / FM",
            color = TextColorSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
fun Mp3PlayerPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Reproductor()
    }
}