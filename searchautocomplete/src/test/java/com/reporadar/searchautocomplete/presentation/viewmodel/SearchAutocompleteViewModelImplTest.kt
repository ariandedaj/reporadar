package com.reporadar.searchautocomplete.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchAutocompleteViewModelImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun search_doesNotStart_untilQueryHasAtLeastThreeNonBlankCharacters() = runTest(testDispatcher) {
        val repository = RecordingGithubSearchRepository()
        val viewModel = SearchAutocompleteViewModelImpl(repository = repository)

        viewModel.onSearchTextChange(newText = "")
        advanceUntilIdle()
        assertTrue(repository.searchQueries.isEmpty())
        assertEquals(SearchAutocompleteUiState.Idle, viewModel.uiState.value)

        viewModel.onSearchTextChange(newText = "a")
        testScheduler.advanceTimeBy(delayTimeMillis = 301L)
        advanceUntilIdle()
        assertTrue(repository.searchQueries.isEmpty())
        assertEquals(SearchAutocompleteUiState.Idle, viewModel.uiState.value)

        viewModel.onSearchTextChange(newText = "ab")
        testScheduler.advanceTimeBy(delayTimeMillis = 301L)
        advanceUntilIdle()
        assertTrue(repository.searchQueries.isEmpty())
        assertEquals(SearchAutocompleteUiState.Idle, viewModel.uiState.value)

        viewModel.onSearchTextChange(newText = "abc")
        testScheduler.advanceTimeBy(delayTimeMillis = 301L)
        advanceUntilIdle()
        assertEquals(listOf("abc"), repository.searchQueries)
        assertTrue(viewModel.uiState.value is SearchAutocompleteUiState.NoResults)
    }

    @Test
    fun rapidInputChanges_debounceEmitsOnlyLastQueryAndPerformsSingleSearch() = runTest(testDispatcher) {
        val repository = RecordingGithubSearchRepository()
        val viewModel = SearchAutocompleteViewModelImpl(repository = repository)

        viewModel.onSearchTextChange(newText = "a")
        testScheduler.advanceTimeBy(delayTimeMillis = 100L)
        viewModel.onSearchTextChange(newText = "ab")
        testScheduler.advanceTimeBy(delayTimeMillis = 100L)
        viewModel.onSearchTextChange(newText = "abc")
        testScheduler.advanceTimeBy(delayTimeMillis = 301L)
        advanceUntilIdle()

        assertEquals(
            "debounce should wait for quiet period after last keystroke, then search once",
            listOf("abc"),
            repository.searchQueries,
        )
        assertTrue(viewModel.uiState.value is SearchAutocompleteUiState.NoResults)
    }

    @Test
    fun rapidInputChanges_collectLatestCancelsStaleSearchWhileRepositoryStillSuspended() =
        runTest(testDispatcher) {
            val repository = DelayingGithubSearchRepository(delayMs = 500L)
            val viewModel = SearchAutocompleteViewModelImpl(repository = repository)

            viewModel.onSearchTextChange(newText = "abc")
            testScheduler.advanceTimeBy(delayTimeMillis = 301L)

            viewModel.onSearchTextChange(newText = "abcd")
            testScheduler.advanceTimeBy(delayTimeMillis = 301L)
            advanceUntilIdle()

            assertEquals(
                "in-flight search for earlier query must not complete once a newer debounced query arrives",
                listOf("abcd"),
                repository.searchQueries,
            )
            assertTrue(viewModel.uiState.value is SearchAutocompleteUiState.NoResults)
        }

}
