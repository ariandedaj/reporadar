package com.reporadar.searchautocomplete.data

import com.reporadar.searchautocomplete.data.model.RepositoryDto
import com.reporadar.searchautocomplete.data.model.RepositoryResultDto
import com.reporadar.searchautocomplete.data.model.UserDto
import com.reporadar.searchautocomplete.data.model.UserResultDto
import com.reporadar.searchautocomplete.data.result.SearchResult
import com.reporadar.searchautocomplete.domain.SearchResultItem
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GithubSearchRepositoryImplTest {

    @Test
    fun search_combinesUsersAndRepositories_sortsAlphabeticallyByDisplayNameCaseInsensitive() =
        runTest {
            val api = FakeGithubApi(
                repos = RepositoryResultDto(
                    items = listOf(
                        RepositoryDto(id = 1, fullName = "Zebra/late"),
                        RepositoryDto(id = 2, fullName = "alpha/early"),
                    )
                ),
                users = UserResultDto(
                    items = listOf(
                        UserDto(id = 3, login = "BetaUser"),
                    )
                ),
            )
            val repository = GithubSearchRepositoryImpl(api = api)

            val result = repository.search(query = "ab")

            assertTrue(result is SearchResult.Success)
            val items = (result as SearchResult.Success).items
            assertEquals(3, items.size)
            assertEquals("alpha/early", (items[0] as SearchResultItem.Repository).repositoryName)
            assertEquals("BetaUser", (items[1] as SearchResultItem.User).loginName)
            assertEquals("Zebra/late", (items[2] as SearchResultItem.Repository).repositoryName)
        }

    @Test
    fun search_returnsFiftyItems_afterMergeAndSort() = runTest {
        val repoItems = (1L..30L).map { id ->
            RepositoryDto(id = id, fullName = "repo-${id.toString().padStart(3, '0')}")
        }
        val userItems = (31L..60L).map { id ->
            UserDto(id = id, login = "user-${id.toString().padStart(3, '0')}")
        }
        val api = FakeGithubApi(
            repos = RepositoryResultDto(items = repoItems),
            users = UserResultDto(items = userItems),
        )
        val repository = GithubSearchRepositoryImpl(api = api)

        val result = repository.search(query = "test")

        assertTrue(
            "expected Success, was $result",
            result is SearchResult.Success
        )
        val items = (result as SearchResult.Success).items
        assertEquals(50, items.size)
    }

}
