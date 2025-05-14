# Reactor Implementation Example

This is a basic example focused on showcasing how to use **MVI** with a simple **Reactor** implementation. The structure is intentionally simple to highlight the core ideas, but it's flexible enough to adapt to more robust architectures such as **Clean Architecture**, and organization patterns like **feature-first** or **layer-first**.

## Architecture

This project follows the **MVI pattern**, which separates concerns into three main components:

- **Model** – Holds the state of the UI.
- **View** – A Compose UI that renders based on the current state.
- **Intent** – User actions or events that are translated into state changes.

## Project structure
```bash
.
├── domain
└── ui/
├── screens/
│   ├── home/
│   │   ├── viewmodel/
│   │   │   ├── HomeEvent.kt
│   │   │   ├── HomeState.kt
│   │   │   └── HomeViewModel.kt
│   │   └── HomeScreen.kt
│   └── login/
│       ├── viewmodel/
│       │   ├── LogInEvent.kt # Event definition (Intent)
│       │   ├── LogInState.kt # State of view (Model)
│       │   └── LogInViewModel.kt
│       └── LogInScreen.kt # Compose UI (View)
├── navigation
├── theme
└── MainActivity.kt
```

## Core Components

- **State** – A data class that represents the UI at any point in time.
- **ReactorEvent** – Sealed class that defines possible user interactions (intents).
- **ReactorViewModel** – Receives events, processes logic, and updates the state.
- **Compose UI** – Observes state and reacts declaratively.

## Getting Started

1. Clone the repo:
    ```bash
      git clone https://github.com/chrisr04/ReactorCompose.git
    ```
2. Open the project in Android Studio.

3. Run the app on an emulator or physical device.