package com.reporadar.searchautocomplete.data.model

import com.reporadar.searchautocomplete.domain.SearchResultItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResultDto(
    val items: List<RepositoryDto>? = null
)

@Serializable
data class RepositoryDto(
    @SerialName("full_name")
    val fullName: String? = null
) {

    @Throws(IllegalArgumentException::class)
    fun toDomain(): SearchResultItem.Repository {
        return SearchResultItem.Repository(
            repositoryName = requireNotNull(this.fullName)
        )
    }

}