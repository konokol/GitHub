# GHStorage 技术方案

## 项目概述

GHStorage 是一个统一的 KV 存储接口抽象库，提供了基于接口的键值存储功能，支持多种底层实现（DataStore、MMKV 等）。

## 架构设计

### 模块划分

```
ghstorage/                    # 核心接口模块
├── kvstore/                  # KV 存储接口定义
│   ├── KVStorage.kt         # 主接口
│   ├── KVStorageFactory.kt  # 工厂接口
│   └── models/              # 数据模型
└── exceptions/              # 异常定义

ghstorage-datastore/         # DataStore 实现模块
├── DataStoreStorage.kt      # DataStore 实现
└── DataStoreFactory.kt      # DataStore 工厂

ghstorage-mmkv/              # MMKV 实现模块
├── MMKVStorage.kt          # MMKV 实现
└── MMKVFactory.kt          # MMKV 工厂
```

### 设计模式

- **工厂模式**: 通过工厂接口创建 KVStorage 实例
- **策略模式**: 不同存储实现可以互相替换
- **依赖倒置原则**: 高层模块依赖接口，不依赖具体实现

## 接口设计

### 核心接口：KVStorage

```kotlin
interface KVStorage {
    /**
     * 存储数据
     */
    suspend fun put(key: String, value: Any): Result<Unit>
    
    /**
     * 获取数据
     */
    suspend fun <T> get(key: String, clazz: Class<T>): Result<T?>
    
    /**
     * 删除数据
     */
    suspend fun remove(key: String): Result<Unit>
    
    /**
     * 清空所有数据
     */
    suspend fun clear(): Result<Unit>
    
    /**
     * 检查键是否存在
     */
    suspend fun contains(key: String): Result<Boolean>
    
    /**
     * 获取所有键
     */
    suspend fun allKeys(): Result<List<String>>
}
```

### 工厂接口：KVStorageFactory

```kotlin
interface KVStorageFactory {
    /**
     * 创建存储实例
     */
    fun create(name: String): KVStorage
    
    /**
     * 创建存储实例并指定加密方式
     */
    fun create(name: String, encryption: EncryptionType): KVStorage
}
```

## 实现方案

### DataStore 实现

**依赖**:
```gradle
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

**特点**:
- 基于协程的异步 API
- 事务保证和错误处理
- 自动数据迁移
- 轻量级且高效

**适用场景**:
- 需要事务保证的场景
- 复杂数据结构存储
- 需要数据迁移的场景

### MMKV 实现

**依赖**:
```gradle
implementation("com.tencent.mmkv:mmkv:1.3.1")
```

**特点**:
- 高性能，读写速度快
- 跨平台支持
- 支持多进程访问
- 内存映射文件

**适用场景**:
- 高性能要求场景
- 多进程访问场景
- 大数据量存储

## 数据模型

### 支持的数据类型

- 基本类型: String, Int, Long, Float, Double, Boolean
- 复合类型: JSON 序列化的对象
- 集合类型: List, Set, Map

### 加密类型

```kotlin
enum class EncryptionType {
    NONE,           // 无加密
    AES_256_GCM,    // AES-256-GCM 加密
    CUSTOM          // 自定义加密
}
```

## 错误处理

### 异常类型

```kotlin
sealed class KVStorageException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class SerializationException(message: String, cause: Throwable?) : KVStorageException(message, cause)
    class EncryptionException(message: String, cause: Throwable?) : KVStorageException(message, cause)
    class StorageException(message: String, cause: Throwable?) : KVStorageException(message, cause)
}
```

## 使用示例

### 基本使用

```kotlin
// 创建 DataStore 实例
val dataStoreFactory = DataStoreFactory(context)
val storage = dataStoreFactory.create("user_preferences")

// 存储数据
storage.put("username", "john_doe")
storage.put("age", 25)
storage.put("isPremium", true)

// 读取数据
val username = storage.get("username", String::class.java).getOrNull()
val age = storage.get("age", Int::class.java).getOrNull()

// 删除数据
storage.remove("username")

// 清空所有
storage.clear()
```

### 切换实现

```kotlin
// 切换到 MMKV 实现
val mmkvFactory = MMKVFactory(context)
val storage = mmkvFactory.create("user_preferences")

// 相同的 API 调用
storage.put("username", "john_doe")
val username = storage.get("username", String::class.java).getOrNull()
```

## 性能考虑

### DataStore 性能特点

- 异步操作，不会阻塞主线程
- 适合中小数据量
- 数据写入有轻微延迟（批量处理）

### MMKV 性能特点

- 同步操作，速度极快
- 适合大数据量和高频访问
- 内存占用相对较高

## 测试策略

### 单元测试

- 接口实现测试
- 数据序列化/反序列化测试
- 错误处理测试

### 集成测试

- 不同实现的兼容性测试
- 并发访问测试
- 数据迁移测试

## 兼容性

- 最低 SDK 版本: API 24 (Android 7.0)
- Kotlin 版本: 1.9.0+
- Coroutines 版本: 1.7.0+

## 安全性

- 敏感数据支持加密存储
- 线程安全保证
- 异常处理和数据完整性保证

## 后续扩展

### 可能的扩展方向

1. 添加更多实现（Realm、SQLite 等）
2. 支持数据同步和备份
3. 添加数据查询和过滤功能
4. 支持分布式存储
5. 添加数据分析和监控功能

### 插件化支持

- 支持自定义加密算法
- 支持自定义序列化方式
- 支持数据压缩和优化

## 项目结构图

```
┌─────────────────────────────────────────────────────────┐
│                    应用层 (App)                          │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│              KVStorage 接口层 (ghstorage)                │
│         KVStorageFactory + KVStorage 接口               │
└──┬────────────────────────────────────────┬─────────────┘
   │                                        │
┌──▼───────────────────┐        ┌──────────▼──────────────┐
│ DataStore 实现        │        │ MMKV 实现              │
│ (ghstorage-datastore) │        │ (ghstorage-mmkv)       │
└───────────────────────┘        └────────────────────────┘
   │                                        │
┌──▼───────────────────┐        ┌──────────▼──────────────┐
│ Android DataStore     │        │ MMKV Native            │
└───────────────────────┘        └────────────────────────┘
```