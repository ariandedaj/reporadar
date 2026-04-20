package com.reporadar.searchautocomplete.data.api

import com.reporadar.searchautocomplete.data.model.RepositoryResultDto
import com.reporadar.searchautocomplete.data.model.UserResultDto

interface GithubApi {

    suspend fun searchRepositories(
        query: String,
        limit: Int
    ): RepositoryResultDto

    suspend fun searchUsers(
        query: String,
        limit: Int
    ): UserResultDto

}