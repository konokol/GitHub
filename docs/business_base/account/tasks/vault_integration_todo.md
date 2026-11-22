# Task Checklist - User Center Vault Integration

- [x] **Core Account & Storage**
    - [x] Recreate `IUserCenter.kt`
    - [x] Restore missing Dagger Core (`GitHub.kt`, `AppComponent.kt`, `AppModule.kt`)
    - [x] Fix `GitHubApplication.java` (typo and package fix)
    - [x] Refine `UserCenterImpl.kt` (Flow-based `isLogin`, storage consistency, loadUser)
- [x] **Login Logic Fixes**
    - [x] Update `LoginActivity.java` / `OAuthActivity.java` (Verified persistence calls)
    - [x] Implement `TokenAuthenticator.kt` (New logic for 401 handling)
    - [x] Register `TokenAuthenticator` in `NetModule.java`
- [x] **Verification**
    - [x] Write basic `UserCenterImplTest`
    - [x] Manual verification of login/logout flow
