package com.reporadar.searchautocomplete.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.reporadar.searchautocomplete.R
import com.reporadar.searchautocomplete.data.result.FetchDataErrorType
import com.reporadar.searchautocomplete.domain.SearchResultItem
import com.reporadar.searchautocomplete.presentation.view.loading.LOADING_VIEW_TAG
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteUiState
import org.junit.Rule
import org.junit.Test

class SearchAutocompleteScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingView() {
        val viewModel = FakeSearchAutocompleteViewModel(
            searchQuery = "abc",
            uiState = SearchAutocompleteUiState.Loading,
        )
        composeRule.setContent {
            MaterialTheme {
                SearchAutocompleteScreen(
                    viewModel = viewModel,
                    onSearchResultItemClick = {},
                    onBackButtonClick = {},
                )
            }
        }

        composeRule
            .onNodeWithTag(testTag = LOADING_VIEW_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun successState_showsSearchResultsView() {
        val userLogin = "success-user"
        val repositoryName = "success-org/success-repo"
        val viewModel = FakeSearchAutocompleteViewModel(
            searchQuery = "abc",
            uiState = SearchAutocompleteUiState.Success(
                items = listOf(
                    SearchResultItem.User(id = 1L, loginName = userLogin),
                    SearchResultItem.Repository(id = 2L, repositoryName = repositoryName),
                ),
            ),
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.setContent {
            MaterialTheme {
                SearchAutocompleteScreen(
                    viewModel = viewModel,
                    onSearchResultItemClick = {},
                    onBackButtonClick = {},
                )
            }
        }

        composeRule
            .onNodeWithText(
                context.getString(R.string.search_result_item_user, userLogin)
            )
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(
                context.getString(R.string.search_result_item_repository, repositoryName)
            )
            .assertIsDisplayed()
    }

    @Test
    fun noResultsState_showsNoResultsView() {
        val query = "no-results-query"
        val viewModel = FakeSearchAutocompleteViewModel(
            searchQuery = query,
            uiState = SearchAutocompleteUiState.NoResults,
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.setContent {
            MaterialTheme {
                SearchAutocompleteScreen(
                    viewModel = viewModel,
                    onSearchResultItemClick = {},
                    onBackButtonClick = {},
                )
            }
        }

        composeRule
            .onNodeWithText(context.getString(R.string.no_results_title))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(context.getString(R.string.no_results_message, query))
            .assertIsDisplayed()
    }

    @Test
    fun failureState_showsErrorView() {
        val viewModel = FakeSearchAutocompleteViewModel(
            searchQuery = "abc",
            uiState = SearchAutocompleteUiState.Failure(
                errorType = FetchDataErrorType.Unknown(exception = RuntimeException()),
            )
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.setContent {
            MaterialTheme {
                SearchAutocompleteScreen(
                    viewModel = viewModel,
                    onSearchResultItemClick = {},
                    onBackButtonClick = {},
                )
            }
        }

        composeRule
            .onNodeWithText(context.getString(R.string.general_unknown_error))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(context.getString(R.string.retry_button))
            .assertIsDisplayed()
    }
}
