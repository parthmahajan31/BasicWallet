package com.task.vpdmoney.util

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

val fadeInTransition = fadeIn(
    animationSpec = tween(durationMillis = 500),
)

val fadeOutTransition = fadeOut(
    animationSpec = tween(durationMillis = 500),
)