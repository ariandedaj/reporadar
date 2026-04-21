package com.reporadar.searchautocomplete.presentation.view.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal const val LOADING_VIEW_TAG = "search_autocomplete_loading"

private val LineWidthFractions = listOf(
    1f,
    0.72f,
    0.88f,
    1f,
    0.55f,
    0.92f,
    0.68f
)

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(LOADING_VIEW_TAG)
            .padding(horizontal = 16.dp)
    ) {
        LineWidthFractions.forEach { lineWidth ->
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(fraction = lineWidth)
                    .height(height = 16.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
                    )
            )
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    LoadingView()
}
