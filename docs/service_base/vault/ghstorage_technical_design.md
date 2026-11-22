# GHStorage 技术方案

## 项目概述

GHStorage 是一个统一的 KV 存储接口抽象库，提供了基于接口的键值存储功能，支持多种底层实现（DataStore、MMKV 等）。

## 架构设计

### 模块划分

```
vault/                       # 核心接口模块 (com.pancoku.vault)
vault-datastore/             # DataStore 实现模块
vault-mmkv/                  # MMKV 实现模块
```

### 设计模式

- **工厂模式**: 通过工厂接口创建 KVStorage 实例
- **策略模式**: 不同存储实现可以互相替换
- **依赖倒置原则**: 高层模块依赖接口，不依赖具体实现

## 接口设计

### 核心接口：KVStorage

```kotlin
interface KVStorage {
    suspend fun put(key: String, value: Any): Result<Unit>
    suspend fun <T> get(key: String, clazz: Class<T>): Result<T?>
    suspend fun remove(key: String): Result<Unit>
    suspend fun clear(): Result<Unit>
    suspend fun contains(key: String): Result<Boolean>
    suspend fun allKeys(): Result<List<String>>
}
```

## 实现方案

### DataStore 实现 (`vault-datastore`)
基于 Preferences DataStore 响应式引擎。

### MMKV 实现 (`vault-mmkv`)
基于 KeyStore 包裹密钥实现 AES-256-CFB 真加密。
