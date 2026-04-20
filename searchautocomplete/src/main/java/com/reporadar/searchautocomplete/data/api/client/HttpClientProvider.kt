package com.reporadar.searchautocomplete.data.api.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    json: Json
): HttpClient {
    return HttpClient(
        engineFactory = Android
    ) {
        install(plugin = ContentNegotiation) { json(json = json) }
        expectSuccess = true
    }
}
