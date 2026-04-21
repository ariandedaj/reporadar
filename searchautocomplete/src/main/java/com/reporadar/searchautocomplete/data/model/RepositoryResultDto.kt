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
    val id: Long? = null,
    @SerialName("full_name")
    val fullName: String? = null
) {

    @Throws(IllegalArgumentException::class)
    fun toDomain(): SearchResultItem.Repository {
        return SearchResultItem.Repository(
            id = requireNotNull(this.id),
            repositoryName = requireNotNull(this.fullName)
        )
    }

}