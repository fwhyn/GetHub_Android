# GetHub Android

An Android application to explore GitHub users that is built using **Kotlin**, **Jetpack Compose**, and **Dagger Hilt**.
The project is designed with support for multiple product flavors and integrates key libraries for modern Android
development.

## Project Structure

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Dagger Hilt
- **Network Layer**: Retrofit
- **Testing**: JUnit, Mockito, Coroutine Test

---

## Requirements

- **Android Studio Meerkat (or newer)**
- **Android SDK 36**
- **JDK version**: 11

---

## Modules and Plugins

### Gradle Plugins Used

- `com.android.application`: Android app plugin
- `org.jetbrains.kotlin.android`: Kotlin support for Android
- `org.jetbrains.kotlin.plugin.compose`: Enables Jetpack Compose
- `com.google.dagger.hilt.android`: Dependency injection with Hilt
- `kotlin-kapt`: For annotation processing with Hilt and other libraries

---

## Build Configuration

### Default Config

- **Application ID**: `com.fwhyn.app.gethub`
- **Min SDK**: 26
- **Target SDK**: 36

### Build Types

- `debug`:
  - Adds `.debug` suffix to the package and `-debug` to the version name
  - Code shrinking is disabled

- `release`:
  - ProGuard is configured but currently disabled (`isMinifyEnabled = false`) until further optimization
  - Use `proguard-rules.pro` for custom rules

---

## Product Flavors

- `real`: Production or real environment build
- `fake`: Used for testing or mock environment

Each flavor belongs to the `default` dimension.

---

## Key Dependencies

### UI

- Jetpack Compose (with BOM)
- AndroidX Core

### Network

- Retrofit2 (with converters)

### DI

- Dagger Hilt (runtime and compiler)

### Testing

- Unit Testing: JUnit, Coroutines Test, Mockito
- Android Testing: JUnit, Compose Testing
