package com.reporadar.searchautocomplete.domain

sealed class SearchResultItem(
    open val id: Long
) {

    data class User(
        override val id: Long,
        val loginName: String
    ) : SearchResultItem(
        id = id
    )

    data class Repository(
        override val id: Long,
        val repositoryName: String
    ) : SearchResultItem(
        id = id
    )

}