package com.reporadar.searchautocomplete.data

import com.reporadar.searchautocomplete.data.api.GithubApi
import com.reporadar.searchautocomplete.data.result.FetchDataErrorType
import com.reporadar.searchautocomplete.data.result.SearchResult
import com.reporadar.searchautocomplete.domain.SearchResultItem
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.SerializationException
import java.io.IOException

class GithubSearchRepositoryImpl(
    private val api: GithubApi
) : GithubSearchRepository {

    override suspend fun search(query: String): SearchResult {
        return try {
            val repositories = api.searchRepositories(query, RESULT_LIMIT)
            val users = api.searchUsers(query, RESULT_LIMIT)

            val repositoryItems = repositories.items.orEmpty().map { it.toDomain() }
            val userItems = users.items.orEmpty().map { it.toDomain() }

            val combinedItems = repositoryItems + userItems

            val sortedItems = combinedItems
                .sortedBy { it.sortKey() }
                .take(RESULT_LIMIT)
            SearchResult.Success(items = sortedItems)
        } catch (e: ClientRequestException) {
            SearchResult.Error(
                errorType = FetchDataErrorType.HttpError(
                    exception = e,
                    httpStatusCode = e.response.status.value
                )
            )
        } catch (e: ServerResponseException) {
            SearchResult.Error(
                errorType = FetchDataErrorType.HttpError(
                    exception = e,
                    httpStatusCode = e.response.status.value
                )
            )
        } catch (e: IOException) {
            SearchResult.Error(errorType = FetchDataErrorType.NoInternet(exception = e))
        } catch (e: SerializationException) {
            SearchResult.Error(errorType = FetchDataErrorType.ParsingFailed(exception = e))
        } catch (e: IllegalArgumentException) {
            SearchResult.Error(errorType = FetchDataErrorType.ResponseMappingFailed(exception = e))
        } catch (e: Exception) {
            SearchResult.Error(errorType = FetchDataErrorType.Unknown(exception = e))
        }
    }

    companion object {
        private const val RESULT_LIMIT = 50
    }
}

private fun SearchResultItem.sortKey(): String =
    when (this) {
        is SearchResultItem.Repository -> repositoryName.lowercase()
        is SearchResultItem.User -> loginName.lowercase()
    }