![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-%238333ff?style=flat-square)

<div align="center"> 
   <img alt="reactor-logo" src="https://github.com/user-attachments/assets/496b82a3-0651-4f28-8b0c-3e36ff01e004"/> 
</div>

<h1 align="center">
    Reactor Implementation Example
</h1>

This is a basic example focused on showcasing how to use **MVI** with a simple **Reactor** implementation. The structure is intentionally simple to highlight the core ideas, but it's flexible enough to adapt to more robust architectures such as **Clean Architecture**, and organization patterns like **feature-first** or **layer-first**.

<p align="center">
  <a href="#topics">Topics</a> •
  <a href="#tecnologies">Tecnologies</a> •
  <a href="#about-project">About project</a> •
  <a href="#demo">Demo</a> •
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

### Architecture

This project follows the **MVI pattern**, which separates concerns into three main components:

- **Model** – Holds the state of the UI.
- **View** – A Compose UI that renders based on the current state.
- **Intent** – User actions or events that are translated into state changes.

### Structure
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

### Core Components

- **State** – A data class that represents the UI at any point in time.
- **ReactorEvent** – Sealed class that defines possible user interactions (intents).
- **ReactorViewModel** – Receives events, processes logic, and updates the state.
- **Compose UI** – Observes state and reacts declaratively.

### MVI Diagram

<p align="center">
  <img src="https://github.com/user-attachments/assets/975a36b5-93ef-4776-b00f-71d2f9579509"/>
</p>

## Demo

<p align="center">
  <img src="https://github.com/user-attachments/assets/8d112017-7ffa-4a44-b386-59943b657edf" alt="Reactor Example" width="300"/>
</p>


## Installation

1. Clone the repo:
    ```bash
      git clone https://github.com/chrisr04/ReactorCompose.git
    ```
2. Open the project in Android Studio.

3. Run the app on an emulator or physical device.

## Author

| [<img src="https://github.com/chrisr04.png" width=115><br><sub>Christian Rodriguez</sub>](https://github.com/chrisr04) | 
|:----------------------------------------------------------------------------------------------------:|
