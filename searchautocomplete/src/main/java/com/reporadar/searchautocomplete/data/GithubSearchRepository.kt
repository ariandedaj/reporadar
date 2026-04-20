package com.reporadar.searchautocomplete.data

import com.reporadar.searchautocomplete.data.result.SearchResult


interface GithubSearchRepository {

    suspend fun search(query: String): SearchResult

}