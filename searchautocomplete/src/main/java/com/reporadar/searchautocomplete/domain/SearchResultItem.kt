package com.reporadar.searchautocomplete.domain

sealed class SearchResultItem {

    data class User(
        val profileName: String
    ) : SearchResultItem()

    data class Repository(
        val repositoryName: String
    )

}