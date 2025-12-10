package com.example.musicapp.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun TopNotchShapeComposable(
    cornerRadius: Dp,
    cutoutRadius: Dp
): Shape {
    val density = LocalDensity.current
    val cornerSizePx = with(density) { cornerRadius.toPx() }
    val cutoutRadiusPx = with(density) { cutoutRadius.toPx() }

    return TopNotchShape(
        cornerSize = cornerSizePx,
        cutoutRadius = cutoutRadiusPx
    )
}