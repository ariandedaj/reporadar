package com.reporadar.searchautocomplete.presentation.viewmodel

import com.reporadar.searchautocomplete.data.GithubSearchRepository
import com.reporadar.searchautocomplete.data.result.SearchResult
import kotlinx.coroutines.delay

class DelayingGithubSearchRepository(
    private val delayMs: Long
) : GithubSearchRepository {

    val searchQueries = mutableListOf<String>()

    override suspend fun search(query: String): SearchResult {
        delay(timeMillis = delayMs)
        searchQueries.add(query)
        return SearchResult.Success(items = emptyList())
    }

}