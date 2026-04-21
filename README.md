# RepoRadar

RepoRadar is a reusable Android (Kotlin) autocomplete component that fetches and displays matching GitHub users and repositories. It combines results into a single alphabetically sorted list, handles rapid input efficiently, and provides clear UI states for loading, empty results, and errors.

## Project Structure

| Module               | Role                                        |
|----------------------|---------------------------------------------|
| `:searchautocomplete`| Reusable UI + ViewModel + Data layer.       |
| `:app`               | Host app (navigation, integration example). |

## Setup

### Gradle

In the **consumer** module (e.g. `app/build.gradle.kts`):

```kotlin
dependencies {
    implementation(project(":searchautocomplete"))
}
```

Ensure `settings.gradle.kts` includes the module:

```kotlin
include(":app")
include(":searchautocomplete")
```

### Network permission

The library performs HTTPS calls to `api.github.com`. The **host application** must declare:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

(Place it under the root `<manifest>` element, alongside `<application>`.)

## Usage

The main entry point is `SearchAutocompleteScreen`. Provide a way to dismiss the screen and a callback when the user selects a search result item:

```kotlin
@Composable
fun MySearchFlow(
    onDismiss: () -> Unit,
    onItemSelected: (SearchResultItem) -> Unit,
) {
    SearchAutocompleteScreen(
        onSearchResultItemClick = onItemSelected,
        onBackButtonClick = onDismiss,
    )
}
```