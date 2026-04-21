# RepoRadar

RepoRadar is a reusable Android (Kotlin) autocomplete component that fetches and displays matching GitHub users and repositories. It combines results into a single alphabetically sorted list, handles rapid input efficiently, and provides clear UI states for loading, empty results, and errors.

## Project requirements

| Requirement | Version |
|-------------|---------|
| **Android Gradle Plugin** | **9.1.1** |
| **Gradle** (wrapper) | **9.3.1** |
| **Kotlin** | **2.3.20** |
| **compileSdk** | **36** |
| **minSdk** | **33** |
| **targetSdk** | **36** |
| **UI** | **Jetpack Compose** (BOM `2026.03.01` in version catalog) |

## Project Structure

| Module               | Role                                        |
|----------------------|---------------------------------------------|
| `:app`               | Host app (navigation, integration example). |
| `:searchautocomplete`| Reusable UI + ViewModel + Data layer.       |

## Usage

### Basic Setup

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

## Using the Component

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