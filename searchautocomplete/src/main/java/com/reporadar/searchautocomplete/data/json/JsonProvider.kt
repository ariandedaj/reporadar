package com.reporadar.searchautocomplete.data.json

import kotlinx.serialization.json.Json

fun createJson(): Json {
    return Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
}