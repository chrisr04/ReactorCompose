![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-%238333ff?style=flat-square)
![License](https://img.shields.io/badge/License-Apache_2.0-%23107ff5?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0.0-%2313c515?style=flat-square)

<!--suppress HtmlDeprecatedAttribute -->
<div align="center"> 
   <img alt="reactor-logo" src="https://github.com/user-attachments/assets/496b82a3-0651-4f28-8b0c-3e36ff01e004"/> 
</div>

<br>

This package enables seamless implementation of the MVI pattern in your Jetpack Compose projects. MVI provides a structured, unidirectional data flow that aligns with Jetpack Compose's reactive nature, ensuring clear state management, easier debugging, and improved scalability.

## Usage
### Creating a ReactorViewModel

To create a ViewModel, extend the `ReactorViewModel` class and provide event and state types. Implement event handlers and register them via `on` method in the init block.

```kotlin
class LogInViewModel() : ReactorViewModel<LogInEvent, LogInState>(LogInState()) {

    init {
        on<ChangeEmailEvent>(::onChangeEmailEvent)
        on<ChangePasswordEvent>(::onChangePasswordEvent)
        on<ValidateUserEvent>(::onValidateUserEvent)
    }

    private fun onChangeEmailEvent(event: ChangeEmailEvent) {
        // Handle your event logic
    }

    private fun onChangePasswordEvent(event: ChangePasswordEvent) {
        // Handle your event logic
    }

    private fun onValidateUserEvent() {
        // Handle your event logic
    }
}

// Define your events
sealed interface LogInEvent : ReactorEvent {
    data class ChangeEmailEvent(val email: String) : LogInEvent
    data class ChangePasswordEvent(val password: String) : LogInEvent
    data object ValidateUserEvent : LogInEvent
}

//Define your state
data class LogInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isFormValid : Boolean = false,
)
```

### Adding Events
To add an event for processing, use the `onEvent` method. Ensure the event is registered with an event handler.

```kotlin
val viewModel = viewModel<LogInViewModel>()
viewModel.onEvent(ValidateUserEvent)
```

### Reacting to State Changes
Collect the state flow using the `collectAsReactorStateWithLifecycle` delegate and react to state changes. It's similar to `collectAsStateWithLifecycle`.

```kotlin
// Route definition
composable<LogIn> {
    val viewModel = viewModel<LogInViewModel>()
    LogInScreen(
        flow = viewModel.flow,
        onEvent = viewModel::onEvent,
    )
}

// Screen implementation
@Composable
fun LogInScreen(
    flow: ReactorFlow<LogInState>,
    onEvent: (LogInEvent) -> Unit,
) {
    val state by flow.collectAsReactorStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            LogInEmailField(
                email = state.email,
                onChangeEmail = { onEvent(ChangeEmailEvent(it)) }
            )
            LogInPasswordField(
                password = state.password,
                readOnly = state.isLoading,
                onChangePassword = { onEvent(ChangePasswordEvent(it)) }
            )
            LogInButton(
                isLoading = state.isLoading,
                onClick = { onEvent(ValidateUserEvent) }
            )
        }
    }
}
```

### ReactorEffect
This composable observes changes in the provided `flow` and executes the effect whenever a new state is emitted. If a `reactWhen` predicate is provided, the function will be called only when the predicate returns true for a given state.

**Basic usage**

```kotlin
ReactorEffect(
    flow,
    reactWhen = { it.isFormValid }
){
    // This is called when a new state is emitted
    // and the isFormValid property is true
}
```

**Advanced usage**

When `mode` is `Startable`, it allows the `onStart` logic to be called only once until the screen is disposed.

```kotlin
ReactorEffect(
    flow,
    mode = EffectMode.Startable
){ state ->

    // This is called when a new state is emitted

    onStart {
        // This is called only once
    }
}
```

### viewModelPreview
This function returns a mock implementation of ReactorViewModel with the given initial state. It's useful for providing a ViewModel instance in a preview environment without requiring actual business logic.

```kotlin
@Preview(showSystemUi = true)
@Composable
fun LogInScreenPreview() {
    val viewModel = viewModelPreview<LogInEvent, LogInState>(
        LogInState(
            email = "preview@email.com",
            password = "1234567",
            isLoading = false
        )
    )
    MyAppTheme {
        LogInScreen(
            flow = viewModel.flow,
            onEvent = {},
        )
    }
}
```

## Installation

**Kotlin**

```kotlin
dependencies {
    implementation("com.animotionsoftware.lib:reactor:{version}")
}
```
**Groovy**

```groovy
dependencies {
    implementation 'com.animotionsoftware.lib:reactor:{version}'
}
```
