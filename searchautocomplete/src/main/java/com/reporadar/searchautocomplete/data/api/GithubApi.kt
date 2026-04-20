package com.reporadar.searchautocomplete.data.api

import com.reporadar.searchautocomplete.data.model.RepositoryResultDto
import com.reporadar.searchautocomplete.data.model.UserResultDto

interface GithubApi {

    suspend fun searchRepositories(query: String): RepositoryResultDto

    suspend fun searchUsers(query: String): UserResultDto

}