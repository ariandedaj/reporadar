package com.reporadar.searchautocomplete.data.model

import com.reporadar.searchautocomplete.domain.Repository
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
    fun UserDto.toDomain(): Repository {
        return Repository(
            name = requireNotNull(this.login)
        )
    }

}