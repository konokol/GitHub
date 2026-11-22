# Implementation Plan - User Center and Login Flow (Vault Integration)

**Status**: Completed ✅
**Date**: 2026-08-25
**Task ID**: ACCOUNT-001

## Goal
Restore the missing `IUserCenter` interface and ensure all authentication credentials (User, Auth Token) are securely stored using the `Vault` (MMKV + AES-256-CFB) via `GHStorage`.

## Proposed Changes

### Core Account & Storage
- [x] **[NEW]** `IUserCenter.kt`: Recreated the missing interface.
- [x] **[MODIFY]** `UserCenterImpl.kt`: Refined to use `GHStorage.getSecureStorage()` and added `isLoginFlow()`.
- [x] **[FIX]** `GitHub.kt`, `AppComponent.kt`, `AppModule.kt`: Restored Dagger core components missing from the codebase.

### Login & Network
- [x] **[NEW]** `TokenAuthenticator.kt`: Automatic logout on 401 Unauthorized errors.
- [x] **[MODIFY]** `NetModule.java`: Registered the new authenticator.
- [x] **[FIX]** `GitHubApplication.java`: Corrected package typos (`pancoku`).

## Verification Plan
- Unit test for `UserCenterImpl`.
- Manual verification of login/persistence/logout flow.
