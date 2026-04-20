package com.reporadar.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reporadar.android.navigation.RepoRadarNavHost
import com.reporadar.android.ui.theme.RepoRadarTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepoRadarTheme {
                RepoRadarNavHost()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RepoRadarPreview() {
    RepoRadarTheme {
        RepoRadarNavHost()
    }
}