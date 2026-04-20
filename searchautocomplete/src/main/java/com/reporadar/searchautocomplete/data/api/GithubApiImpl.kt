package com.reporadar.searchautocomplete.data.api

import com.reporadar.searchautocomplete.data.model.RepositoryResultDto
import com.reporadar.searchautocomplete.data.model.UserResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GithubApiImpl(
    private val httpClient: HttpClient
) : GithubApi {

    override suspend fun searchRepositories(
        query: String,
        limit: Int
    ): RepositoryResultDto {
        val response = httpClient.get(
            urlString = "$GITHUB_SEARCH_API_URL/$GITHUB_REPOSITORIES_PATH"
        ) {
            parameter("q", query)
            parameter("per_page", limit)
        }
        return response.body<RepositoryResultDto>()
    }

    override suspend fun searchUsers(
        query: String,
        limit: Int
    ): UserResultDto {
        val response = httpClient.get(
            urlString = "$GITHUB_SEARCH_API_URL/$GITHUB_USERS_PATH"
        ) {
            parameter("q", query)
            parameter("per_page", limit)
        }
        return response.body<UserResultDto>()
    }

    companion object {
        private const val GITHUB_SEARCH_API_URL = "https://api.github.com/search"
        private const val GITHUB_REPOSITORIES_PATH = "repositories"
        private const val GITHUB_USERS_PATH = "users"
    }

}