package com.reporadar.searchautocomplete.presentation.view

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.reporadar.searchautocomplete.R

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = stringResource(id = R.string.back_button_content_description)
        )
    }
}