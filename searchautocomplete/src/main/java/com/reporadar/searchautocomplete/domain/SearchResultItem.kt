package com.reporadar.searchautocomplete.domain

sealed class SearchResultItem {

    data class User(
        val loginName: String
    ) : SearchResultItem()

    data class Repository(
        val repositoryName: String
    ) : SearchResultItem()

}