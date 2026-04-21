package com.reporadar.searchautocomplete.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class SearchResultItem : Parcelable {
    abstract val id: Long

    @Parcelize
    data class User(
        override val id: Long,
        val loginName: String,
    ) : SearchResultItem()

    @Parcelize
    data class Repository(
        override val id: Long,
        val repositoryName: String,
    ) : SearchResultItem()
}