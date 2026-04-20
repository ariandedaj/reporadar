package com.reporadar.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
fun slideInFromRight() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(durationMillis = ANIMATION_DURATION)
)

@OptIn(ExperimentalAnimationApi::class)
fun slideOutToLeft() = slideOutHorizontally(
    targetOffsetX = { -it / 3 },
    animationSpec = tween(durationMillis = ANIMATION_DURATION)
)

@OptIn(ExperimentalAnimationApi::class)
fun slideInFromLeft() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(durationMillis = ANIMATION_DURATION)
)

@OptIn(ExperimentalAnimationApi::class)
fun slideOutToRight() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(durationMillis = ANIMATION_DURATION)
)