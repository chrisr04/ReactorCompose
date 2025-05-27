![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-%238333ff?style=flat-square)
![License](https://img.shields.io/badge/License-Apache_2.0-%23107ff5?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0.0-%2313c515?style=flat-square)

<!--suppress HtmlDeprecatedAttribute -->
<div align="center"> 
   <img alt="reactor-logo" src="https://github.com/user-attachments/assets/496b82a3-0651-4f28-8b0c-3e36ff01e004"/> 
</div>

<h1 align="center">Reactor</h1>

This package enables seamless implementation of the MVI pattern in your Jetpack Compose projects. MVI provides a structured, unidirectional data flow that aligns with Jetpack Compose's reactive nature, ensuring clear state management, easier debugging, and improved scalability.

<p align="center">
  <a href="#topics">Topics</a> •
  <a href="#tecnologies">Tecnologies</a> •
  <a href="#about-project">About project</a> •
  <a href="#usage">Usage</a> •
  <a href="#installation">Installation</a> •
  <a href="#author">Author</a>
</p>

## Topics

* Android
* Jetpack Compose
* MVI
* Kotlin

## Tecnologies
### This project uses:
- [Kotlin] version 2.0.21

## About project
### What's MVI?
The MVI (Model–View–Intent) pattern is an architecture pattern that ensures data flows in a single direction, which helps keep your application more predictable and easier to debug. In short, it will be easier to find bugs, and you’ll be able to test each component of the application in a easy way.

### MVI Diagram

<p align="center">
  <img src="https://github.com/user-attachments/assets/975a36b5-93ef-4776-b00f-71d2f9579509"/>
</p>

### Why Reactor?

One of the main drawbacks of the MVI pattern is the amount of boilerplate code required for event handling. To address this, I created a lightweight package called Reactor that enables a cleaner and more streamlined implementation within the ViewModel, while keeping the view layer simple and easy to maintain.

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
        emit(state.copy(email = event.email))
    }

    private fun onChangePasswordEvent(event: ChangePasswordEvent) {
        emit(state.copy(password = event.password))
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

// Define your state
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

## Author

| [<img src="https://github.com/chrisr04.png" width=115><br><sub>Christian Rodriguez</sub>](https://github.com/chrisr04) | 
|:----------------------------------------------------------------------------------------------------:|
