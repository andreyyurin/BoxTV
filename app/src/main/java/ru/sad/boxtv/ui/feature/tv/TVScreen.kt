package ru.sad.boxtv.ui.feature.tv

import android.media.tv.TvView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import kotlinx.coroutines.isActive
import org.koin.compose.viewmodel.koinViewModel
import ru.sad.boxtv.R
import ru.sad.boxtv.ui.TvViewScreen
import ru.sad.tvframework.api.ChannelModel

@Composable
fun TVScreen(
    viewModel: TVViewModel = koinViewModel(),
    navController: NavController,
) {

    val state by viewModel.channelsState.collectAsState()

    when (val data = state) {
        TVState.Loading -> Loading()
        is TVState.Success -> ListChannels(
            channels = data.channels,
            onClick = {
                navController.navigate(route = TvViewScreen(it))
            }
        )

        is TVState.Error -> Error()
    }
}

@Composable
private fun ListChannels(
    channels: List<ChannelModel>,
    onClick: (ChannelModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(channels) { channel ->
            ChannelItem(channel, onClick)
        }
    }
}

@Composable
private fun ChannelItem(channel: ChannelModel, onClick: (ChannelModel) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .sizeIn(minHeight = 200.dp)
            .background(
                if (isFocused) {
                    Color(0x1A00FFFF)
                } else {
                    Color(0xFF00FFFF)
                }
            )
            .clickable {
                onClick.invoke(channel)
            }
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .focusable(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = channel.id.orDefault("no channel"))
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val isRotating by remember { mutableStateOf(true) }

        val rotate = remember { Animatable(0f) }
        val target = 360f
        if (isRotating) {
            LaunchedEffect(Unit) {
                while (isActive) {
                    val remaining = (target - rotate.value) / target
                    rotate.animateTo(
                        target,
                        animationSpec = tween(
                            (1_000 * remaining).toInt(),
                            easing = LinearEasing
                        )
                    )
                    rotate.snapTo(0f)
                }
            }
        }

        Image(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
                .run { rotate(rotate.value) },
            painter = painterResource(R.drawable.ic_cyclone),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = null
        )
    }
}

@Composable
private fun Error() {
    Text("Oooops!")
}

private fun String?.orDefault(any: String) = this ?: any