package com.example.musicapp.screens.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TopNotchShape(
    private val cornerSize: Float,
    private val cutoutRadius: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                // Variables de conveniencia
                val centerX = size.width / 2f
                val corner2X = cornerSize * 2
                val height = size.height
                val width = size.width


                val notchWidth = cutoutRadius * 3.5f // El ancho de la muesca (e.g., 70.dp)
                val notchDepth = cutoutRadius * 1.25f // **¡Clave! Profundidad de la muesca (e.g., 10.dp)**

                val startX = centerX - (notchWidth / 2)
                val endX = centerX + (notchWidth / 2)

                moveTo(cornerSize, 0f)

                lineTo(startX, 0f)


                quadraticBezierTo(
                    x1 = centerX,
                    y1 = notchDepth * 1.5f,
                    x2 = endX,
                    y2 = 0f
                )

                // 4. Línea hasta la esquina superior derecha
                lineTo(width - cornerSize, 0f)

                // 5. Esquina superior derecha (Arco)
                arcTo(
                    rect = Rect(
                        left = width - corner2X,
                        top = 0f,
                        right = width,
                        bottom = corner2X
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // 6. Dibujar el resto de la forma (bordes inferiores y laterales)
                lineTo(width, height - cornerSize)
                arcTo(
                    rect = Rect(
                        left = width - corner2X,
                        top = height - corner2X,
                        right = width,
                        bottom = height
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(cornerSize, height)
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = height - corner2X,
                        right = corner2X,
                        bottom = height
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(0f, cornerSize)
                // Esquina superior izquierda
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = corner2X,
                        bottom = corner2X
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                close()
            }
        )
    }
}