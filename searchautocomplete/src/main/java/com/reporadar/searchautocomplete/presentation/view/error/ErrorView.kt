package com.reporadar.searchautocomplete.presentation.view.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.reporadar.searchautocomplete.R
import com.reporadar.searchautocomplete.data.result.FetchDataErrorType

@Composable
fun ErrorView(
    errorType: FetchDataErrorType,
    onRetryButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(size = 120.dp),
            painter = painterResource(id = R.drawable.unknown_error_icon),
            contentDescription = stringResource(id = R.string.unknown_error_icon_description)
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
        Text(
            text = getHumanReadableError(errorType = errorType),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
        Button(
            onClick = onRetryButtonClick
        ) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}