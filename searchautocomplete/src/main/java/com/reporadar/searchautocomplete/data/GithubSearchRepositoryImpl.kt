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

            val combined = (repositoryItems + userItems).sortedWith(
                comparator = compareBy(comparator = String.CASE_INSENSITIVE_ORDER) { item ->
                    when (item) {
                        is SearchResultItem.Repository -> item.repositoryName
                        is SearchResultItem.User -> item.loginName
                    }
                }
            )
            SearchResult.Success(items = combined)
        } catch (e: ClientRequestException) {
            SearchResult.Error(
                FetchDataErrorType.HttpError(
                    exception = e,
                    httpStatusCode = e.response.status.value
                )
            )
        } catch (e: ServerResponseException) {
            SearchResult.Error(
                FetchDataErrorType.HttpError(
                    exception = e,
                    httpStatusCode = e.response.status.value
                )
            )
        } catch (e: IOException) {
            SearchResult.Error(FetchDataErrorType.NoInternet(exception = e))
        } catch (e: SerializationException) {
            SearchResult.Error(FetchDataErrorType.ParsingFailed(exception = e))
        } catch (e: IllegalArgumentException) {
            SearchResult.Error(FetchDataErrorType.ResponseMappingFailed(exception = e))
        } catch (e: Exception) {
            SearchResult.Error(FetchDataErrorType.Unknown(exception = e))
        }
    }

    companion object {
        private const val RESULT_LIMIT = 50
    }

}
