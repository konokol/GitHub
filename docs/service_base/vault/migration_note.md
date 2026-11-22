# GHStorage 迁移与重构记录

## 日期: 2026-08-22

## 重构概览

1. **包名规范**：统一修改为 `com.pancoku.vault`（核心）、`com.pancoku.vault.datastore`、`com.pancoku.vault.mmkv`。
2. **True AES-256-CFB 加密**：通过 `MMKVCryptKeyProvider` 利用 AndroidKeyStore 实现真正的硬件隔离加密。
3. **DataStoreManager**：实现单例模式，解决 DataStore 实例重复创建可能导致的崩溃。
4. **App 接入点**：新建 `GHStorage.kt`，供 `UserCenterImpl` 等核心逻辑安全持久化 Token。
