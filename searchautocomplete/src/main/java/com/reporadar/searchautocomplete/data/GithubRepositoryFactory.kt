package com.reporadar.searchautocomplete.data

import com.reporadar.searchautocomplete.data.api.createGithubApi

fun createGithubRepository(): GithubSearchRepository {
    return GithubSearchRepositoryImpl(
        api = createGithubApi()
    )
}