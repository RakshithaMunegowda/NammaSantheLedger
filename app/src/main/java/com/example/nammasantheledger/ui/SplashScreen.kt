package com.example.nammasantheledger.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF7F9CF5),
                        Color(0xFFB794F4)
                    )
                )
            )
    ) {

        // 🌿 Floating Emojis Background
        FloatingEmojis()

        // 🎯 Center Logo
        CenterLogo()
    }
}

@Composable
fun CenterLogo() {

    val scaleAnim = rememberInfiniteTransition()

    val scale by scaleAnim.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .shadow(10.dp, CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "ನಮ್ಮ ಸಂತೆ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F855A)
                )

                Text(
                    text = "Ledger",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun FloatingEmojis() {

    val emojis = listOf(
        "🍅","🥕","🌶️","🍆","🥬","🧅","🥔",
        "🍉","🥭","🍇","🍍","🥒","🍳","🥎","🛺"
    )

    val infiniteTransition = rememberInfiniteTransition()

    Box(modifier = Modifier.fillMaxSize()) {

        emojis.forEach { emoji ->

            val startX = (0..300).random().toFloat()
            val startY = (0..800).random().toFloat()

            val offsetX = infiniteTransition.animateFloat(
                initialValue = startX,
                targetValue = startX + (-100..100).random().toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (3000..6000).random(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            val offsetY = infiniteTransition.animateFloat(
                initialValue = startY,
                targetValue = startY + (-200..200).random().toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (3000..6000).random(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            Text(
                text = emoji,
                fontSize = (24..40).random().sp,
                modifier = Modifier.offset(
                    x = offsetX.value.dp,
                    y = offsetY.value.dp
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
