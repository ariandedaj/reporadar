package com.reporadar.searchautocomplete.data.api

import com.reporadar.searchautocomplete.data.api.client.createHttpClient
import com.reporadar.searchautocomplete.data.json.createJson

fun createGithubApi(): GithubApi {
    return GithubApiImpl(
        httpClient = createHttpClient(
            json = createJson()
        )
    )
}