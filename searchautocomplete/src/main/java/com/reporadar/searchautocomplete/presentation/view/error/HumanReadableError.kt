package com.reporadar.searchautocomplete.presentation.view.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.reporadar.searchautocomplete.R
import com.reporadar.searchautocomplete.data.result.FetchDataErrorType

@Composable
fun getHumanReadableError(
    errorType: FetchDataErrorType
): String {
    return when (errorType) {
        is FetchDataErrorType.Unknown -> {
            stringResource(id = R.string.general_unknown_error)
        }

        is FetchDataErrorType.NoInternet -> {
            stringResource(id = R.string.general_error_no_internet_connection)
        }

        is FetchDataErrorType.ParsingFailed -> {
            stringResource(id = R.string.general_unknown_error)
        }

        is FetchDataErrorType.ResponseMappingFailed -> {
            stringResource(id = R.string.general_unknown_error)
        }

        is FetchDataErrorType.HttpError -> {
            when (errorType.httpStatusCode) {
                in 400..499 -> stringResource(
                    id = R.string.general_error_request_failed,
                    errorType.httpStatusCode
                )

                in 500..599 -> stringResource(
                    id = R.string.general_error_service_unavailable,
                    errorType.httpStatusCode
                )

                else -> stringResource(
                    id = R.string.general_error_request_failed,
                    errorType.httpStatusCode
                )
            }
        }
    }
}