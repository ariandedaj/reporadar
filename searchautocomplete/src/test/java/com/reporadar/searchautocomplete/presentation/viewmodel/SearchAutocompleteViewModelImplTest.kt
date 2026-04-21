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

}
