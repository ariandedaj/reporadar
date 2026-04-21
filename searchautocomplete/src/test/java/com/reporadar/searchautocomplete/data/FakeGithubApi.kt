package com.reporadar.searchautocomplete.data

import com.reporadar.searchautocomplete.data.api.GithubApi
import com.reporadar.searchautocomplete.data.model.RepositoryResultDto
import com.reporadar.searchautocomplete.data.model.UserResultDto

class FakeGithubApi(
    private val repos: RepositoryResultDto,
    private val users: UserResultDto,
) : GithubApi {
    override suspend fun searchRepositories(query: String, limit: Int) = repos

    override suspend fun searchUsers(query: String, limit: Int) = users
}