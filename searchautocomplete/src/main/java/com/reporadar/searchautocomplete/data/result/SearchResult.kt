package com.reporadar.searchautocomplete.data.result

import com.reporadar.searchautocomplete.domain.SearchResultItem

sealed class SearchResult {

    data class Success(
        val items: List<SearchResultItem>
    ) : SearchResult()

    data class Error(
        val errorType: FetchDataErrorType
    ) : SearchResult()

}

sealed class FetchDataErrorType(
    open val exception: Exception? = null
) {

    data class NoInternet(
        override val exception: Exception? = null
    ) : FetchDataErrorType()

    data class ParsingFailed(
        override val exception: Exception? = null
    ) : FetchDataErrorType()

    data class ResponseMappingFailed(
        override val exception: Exception? = null
    ) : FetchDataErrorType()

    data class HttpError(
        override val exception: Exception? = null,
        val httpStatusCode: Int
    ) : FetchDataErrorType()

    data class Unknown(
        override val exception: Exception? = null
    ) : FetchDataErrorType()

}