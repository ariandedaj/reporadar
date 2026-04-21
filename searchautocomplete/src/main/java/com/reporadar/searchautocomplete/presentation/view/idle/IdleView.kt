package com.reporadar.searchautocomplete.presentation.view.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.reporadar.searchautocomplete.R

@Composable
fun IdleView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 48.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null,
                modifier = Modifier.size(size = 20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)
            )

            Spacer(modifier = Modifier.width(width = 8.dp))

            Text(
                text = stringResource(id = R.string.idle_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(height = 8.dp))

        Text(
            text = stringResource(id = R.string.idle_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
