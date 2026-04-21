package com.reporadar.searchautocomplete.presentation.viewmodel

import com.reporadar.searchautocomplete.data.GithubSearchRepository
import com.reporadar.searchautocomplete.data.result.SearchResult

class RecordingGithubSearchRepository : GithubSearchRepository {

    val searchQueries = mutableListOf<String>()

    override suspend fun search(query: String): SearchResult {
        searchQueries.add(query)
        return SearchResult.Success(items = emptyList())
    }

}