package org.example.ticketmaster.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun NavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.ConfirmationNumber,
        Icons.Default.ShoppingCart,
        Icons.Default.Person
    )

    val barColor = Color(0xFF16161D)
    val beadColor = Color(0xFF00E5FF)
    val density = LocalDensity.current

    var barWidth by remember {
        mutableStateOf(0f)
    }

    var dragX by remember {
        mutableStateOf<Float?>(null)
    }

    var isDragging by remember {
        mutableStateOf(false)
    }

    val segmentWidth = if (barWidth > 0f) {
        barWidth / icons.size
    } else {
        0f
    }

    val selectedX = if (segmentWidth > 0f) {
        selectedIndex * segmentWidth + segmentWidth / 2f
    } else {
        0f
    }

    val targetX = dragX ?: selectedX

    val animatedX by animateFloatAsState(
        targetValue = targetX,
        animationSpec = if (isDragging) {
            tween(durationMillis = 0)
        } else {
            spring(
                dampingRatio = 0.75f,
                stiffness = 500f
            )
        },
        label = "beadPosition"
    )

    val beadSize by animateDpAsState(
        targetValue = if (isDragging) 56.dp else 50.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "beadSize"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .align(Alignment.BottomCenter)
                .onSizeChanged {
                    barWidth = it.width.toFloat()
                }
        ) {
            val width = size.width
            val height = size.height

            val radius = 32.dp.toPx()
            val cutoutRadius = 29.dp.toPx()
            val cutoutDepth = 26.dp.toPx()
            val centerX = animatedX

            val path = Path()

            path.moveTo(radius, 0f)

            path.lineTo(
                centerX - cutoutRadius,
                0f
            )

            path.cubicTo(
                centerX - cutoutRadius * 0.65f,
                0f,
                centerX - cutoutRadius * 0.65f,
                cutoutDepth,
                centerX,
                cutoutDepth
            )

            path.cubicTo(
                centerX + cutoutRadius * 0.65f,
                cutoutDepth,
                centerX + cutoutRadius * 0.65f,
                0f,
                centerX + cutoutRadius,
                0f
            )

            path.lineTo(
                width - radius,
                0f
            )

            path.quadraticTo(
                width,
                0f,
                width,
                radius
            )

            path.lineTo(
                width,
                height - radius
            )

            path.quadraticTo(
                width,
                height,
                width - radius,
                height
            )

            path.lineTo(
                radius,
                height
            )

            path.quadraticTo(
                0f,
                height,
                0f,
                height - radius
            )

            path.lineTo(
                0f,
                radius
            )

            path.quadraticTo(
                0f,
                0f,
                radius,
                0f
            )

            path.close()

            drawPath(
                path = path,
                color = barColor
            )
        }

        icons.forEachIndexed { index, icon ->
            val isSelected = selectedIndex == index

            val iconX = if (segmentWidth > 0f) {
                index * segmentWidth + segmentWidth / 2f
            } else {
                0f
            }

            val alpha by animateFloatAsState(
                targetValue = if (isSelected) 0f else 0.55f,
                animationSpec = tween(
                    durationMillis = 200
                ),
                label = "iconAlpha"
            )

            IconButton(
                onClick = {
                    onItemSelected(index)
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset {
                        IntOffset(
                            x = (
                                    iconX -
                                            with(density) {
                                                28.dp.toPx()
                                            }
                                    ).roundToInt(),
                            y = with(density) {
                                0.dp.toPx().roundToInt()
                            }
                        )
                    }
                    .height(72.dp)
                    .fillMaxWidth(fraction = 0.25f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = alpha),
                    modifier = Modifier
                        .height(24.dp)
                )
            }
        }

        if (barWidth > 0f) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset {
                        IntOffset(
                            x = (
                                    animatedX -
                                            with(density) {
                                                beadSize.toPx() / 2f
                                            }
                                    ).roundToInt(),
                            y = with(density) {
                                (-13).dp.toPx().roundToInt()
                            }
                        )
                    }
                    .height(beadSize)
                    .fillMaxWidth(fraction = 0.125f)
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        ambientColor = beadColor,
                        spotColor = beadColor
                    )
                    .clip(CircleShape)
                    .background(beadColor)
                    .pointerInput(segmentWidth) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                                dragX = animatedX
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()

                                val newX =
                                    (dragX ?: animatedX) + dragAmount.x

                                val minX = segmentWidth / 2f
                                val maxX = barWidth - segmentWidth / 2f

                                dragX = newX.coerceIn(
                                    minX,
                                    maxX
                                )
                            },
                            onDragEnd = {
                                if (segmentWidth > 0f) {
                                    val index =
                                        ((dragX ?: animatedX) / segmentWidth)
                                            .roundToInt()
                                            .coerceIn(
                                                0,
                                                icons.lastIndex
                                            )

                                    isDragging = false
                                    dragX = null

                                    onItemSelected(index)
                                }
                            },
                            onDragCancel = {
                                isDragging = false
                                dragX = null
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = selectedIndex,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 180
                            )
                        ) togetherWith fadeOut(
                            animationSpec = tween(
                                durationMillis = 120
                            )
                        )
                    },
                    label = "selectedIcon"
                ) { index ->
                    Icon(
                        imageVector = icons[index],
                        contentDescription = null,
                        tint = barColor,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}