package com.reporadar.searchautocomplete.data.model

import com.reporadar.searchautocomplete.domain.SearchResultItem
import kotlinx.serialization.Serializable

@Serializable
data class UserResultDto(
    val items: List<UserDto>? = null
) {
}

@Serializable
data class UserDto(
    val login: String? = null
) {

    @Throws(IllegalArgumentException::class)
    fun toDomain(): SearchResultItem.User {
        return SearchResultItem.User(
            loginName = requireNotNull(this.login)
        )
    }

}