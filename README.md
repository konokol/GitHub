# GitHub Android Client

[![GitHub API](https://img.shields.io/badge/API-GitHub%20REST%20v3-181717?logo=github)](https://docs.github.com/en/rest)
[![Android SDK](https://img.shields.io/badge/Android-API%2021%2B-3DDC84?logo=android)](https://developer.android.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.10.1-02303A?logo=gradle)](https://gradle.org/)
[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://www.oracle.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

An Android application built on top of the official **GitHub REST API**, providing a mobile experience for browsing GitHub activity feeds, managing user authentication, and viewing user profiles.

---

## ✨ Implemented Features

- **GitHub REST API Integration**: Connected to GitHub API endpoints for user authentication, activity feeds, and profile data.
- **OAuth 2.0 & Token Login**: Supports Web OAuth 2.0 flow and token authentication with encrypted local storage (`SecureSharedPreference`).
- **Activity Feed**: Paginated feed streaming GitHub user timeline events with specialized UI rendering for `PushEvent` and `DeleteEvent`.
- **Modular App Startup**: Modularized, async app initialization chain powered by `androidx.startup`.
- **Custom UI Component Library (`:library`)**: Reusable UI module containing `SwipeRefreshRecyclerView`, state layouts (`EmptyView`, `LoadingView`), custom indicators, and logging framework.
- **Hybrid Web Container**: Integrated `BifrostWebView` for in-app browsing of GitHub repositories and web links.

---

## 🚧 Roadmap & Unfinished Features (TODOs)

- [ ] **Import Feature (`nav_camera`)**: Implement side-drawer Import module stub in `FragmentProvider`.
- [ ] **Gallery Feature (`nav_gallery`)**: Implement side-drawer Gallery module stub in `FragmentProvider`.
- [ ] **Slideshow Feature (`nav_slideshow`)**: Implement side-drawer Slideshow module stub in `FragmentProvider`.
- [ ] **Tools Feature (`nav_manage`)**: Implement side-drawer Tools/Management module stub in `FragmentProvider`.
- [ ] **Share Feature (`nav_share`)**: Implement side-drawer Share action stub in `FragmentProvider`.
- [ ] **Send Feature (`nav_send`)**: Implement side-drawer Send action stub in `FragmentProvider`.
- [ ] **Web Activity Share Action**: Implement web page sharing in `WebActivity` (currently shows a `"Not implemented yet!"` snackbar stub).
- [ ] **User Avatar Media Preview**: Complete user avatar preview and full profile media inspection in `ProfileView`.
- [ ] **Full Event ViewHolder Support**: Extend custom UI cards for `ForkEvent`, `WatchEvent`, `IssueCommentEvent`, `CreateEvent`, `ReleaseEvent`, and `PullRequestEvent` (currently defaulting to fallback views).
- [ ] **Notification Center**: Fully implement `NotificationFragment` to fetch and manage GitHub notifications via API.
- [ ] **Repository & Code Browser**: Add repository search, source code file tree navigation, and syntax-highlighted code viewer.
- [ ] **Issue & Pull Request Management**: Enable creating, inspecting, and commenting on Issues and Pull Requests.
- [ ] **Drawer Navigation Actions**: Wire up remaining side-drawer navigation menu items (`nav_camera`, `nav_gallery`, etc.).

---

## 🛠 Tech Stack

- **Language & Platform**: Java 17, Android SDK 35 (minSdk 21)
- **Architecture**: MVP (Model-View-Presenter)
- **Networking**: Retrofit 2 + RxJava 3 + OkHttp 3 (GitHub REST API)
- **Dependency Injection**: Dagger 2
- **UI Components**: Material Components, ViewBinding / DataBinding, Glide 4

---

## 🚀 Getting Started

### Prerequisites

- **JDK**: 17
- **Android SDK**: 35 (compileSdk & targetSdk)
- **Android Studio**: Ladybug / 2024.2+ recommended

### Building & Testing

- **Build Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
- **Run Unit Tests**:
  ```bash
  ./gradlew test
  ```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
