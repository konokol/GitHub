# AppMock 模块改进 - 技术方案文档

## 📋 文档说明

本文档详细描述了 AppMock 模块需要改进的技术问题和相应的解决方案。

**关联文档：**
- 📄 [待办计划文档](appmock_task_plan.md)

---

## 项目概述

AppMock 是一个 OkHttp 网络请求拦截器库，支持按用户标识将网络请求转发到不同的代理服务器。

### 当前架构

**现有组件：**
- **AppMock.kt** - 门面类，管理多用户配置和拦截器创建
- **MockConfig.kt** - 单用户配置数据类
- **ProxyInterceptor.kt** - OkHttp 拦截器，实现请求转发

**现有优势：**
- ✅ 职责分离清晰
- ✅ 支持多用户配置隔离
- ✅ 提供链式调用API
- ✅ 包含基础单元测试

---

## 需要改进的问题分析

### 🔴 高优先级（影响稳定性）

#### 1. 配置持久化 ⭐ **核心问题**

**问题描述：**
当前配置只存储在内存中，应用重启后会丢失。

**影响范围：**
- 用户需要在每次应用启动后重新配置代理
- 无法实现自动化的持续测试
- 用户体验差，需要手动管理配置

**技术难点：**
- 需要选择合适的持久化方案（SharedPreferences / DataStore / Room）
- 需要处理异步加载和线程同步
- 需要保证数据一致性和事务性
- 需要考虑数据迁移和版本管理

**推荐解决方案：**
- 使用 Android DataStore（Preferences）
- 实现配置的异步保存和加载
- 提供配置变更监听（Flow）
- 支持配置的导入/导出

**参考技术方案：** [配置持久化详细方案](#配置持久化详细方案)

---

#### 2. 线程安全性

**问题描述：**
`AppMock.configs` 使用 `mutableMapOf`，在多线程环境下存在数据竞争风险。

**影响范围：**
- 并发设置配置可能导致数据丢失或覆盖
- 在多线程网络请求中可能出现不可预期行为

**技术难点：**
- OkHttp 的拦截器在后台线程执行
- 配置的读写可能同时发生
- 需要保证读写操作的原子性

**推荐解决方案：**
- 使用 `ConcurrentHashMap` 替代 `mutableMapOf`
- 或使用 `synchronized` 保护关键操作

**参考技术方案：** [线程安全详细方案](#线程安全详细方案)

---

#### 3. 异常处理

**问题描述：**
`ProxyInterceptor` 没有异常处理，代理服务器不可用会导致请求失败。

**影响范围：**
- 代理连接失败会导致整个请求链中断
- 没有降级机制，影响应用稳定性
- 缺少错误信息，难以排查问题

**技术难点：**
- 需要区分不同类型的异常（网络异常、超时、配置错误）
- 需要实现优雅的降级策略
- 需要提供错误回调机制

**推荐解决方案：**
- 添加 try-catch 异常捕获
- 实现降级策略（代理失败时走原始请求）
- 添加错误回调接口
- 记录详细的错误日志

**参考技术方案：** [异常处理详细方案](#异常处理详细方案)

---

#### 4. 配置验证

**问题描述：**
没有验证代理服务器地址的有效性。

**影响范围：**
- 可以设置空的主机名
- 可以设置无效的端口号（< 0 或 > 65535）
- 只在请求时才发现配置错误

**技术难点：**
- 需要实现配置验证逻辑
- 需要提供友好的错误提示
- 需要设计验证 API

**推荐解决方案：**
- 添加参数校验逻辑
- 提供配置验证接口
- 添加配置状态查询

---

#### 5. 日志记录

**问题描述：**
缺少日志记录，难以调试和监控代理行为。

**影响范围：**
- 无法追踪请求是否被代理
- 难以排查代理相关问题
- 缺少使用统计信息

**推荐解决方案：**
- 集成日志框架（如 Timber）
- 记录关键操作和状态变化
- 提供日志开关配置

---

### 🟡 中优先级（提升可用性）

#### 6. 配置管理 API

**问题描述：**
缺少完整的配置管理功能。

**缺失功能：**
- 删除指定用户配置
- 清空所有配置
- 获取所有用户列表
- 配置查询和统计

---

#### 7. 代码质量

**问题描述：**
存在代码风格问题。

- `MockConfig.kt` 缺少尾随逗号
- 需要添加更多 KDoc 文档
- 需要添加使用示例

---

#### 8. 测试覆盖

**问题描述：**
测试覆盖不够全面。

**需要补充的测试：**
- 边界条件测试
- 异常情况测试
- 并发测试
- 配置验证测试

---

### 🟢 低优先级（增强功能）

#### 9. 高级代理配置
- 支持 HTTPS 代理
- 支持代理认证
- 支持超时配置
- 支持白名单/黑名单

#### 10. 性能监控
- 请求耗时统计
- 代理成功率统计
- 配置使用分析

---

## 详细技术方案

### 配置持久化详细方案

#### 方案选择对比

| 方案 | 优点 | 缺点 | 推荐度 |
|------|------|------|--------|
| SharedPreferences | 简单易用，无需额外依赖 | 同步操作，性能较差 | ⭐⭐⭐ |
| DataStore | 异步操作，性能好，类型安全 | API 相对复杂 | ⭐⭐⭐⭐⭐ |
| Room | 功能强大，支持复杂查询 | 引入重量级依赖 | ⭐⭐ |

#### 推荐方案：DataStore

**选择理由：**
1. Google 推荐的现代化解决方案
2. 异步 API，不阻塞主线程
3. 类型安全，编译时检查
4. 事务性更新，保证数据一致性
5. 自动处理数据迁移

#### 架构设计

```
┌─────────────────────────────────────────────────────────┐
│                     AppMock (Facade)                      │
│  - newInterceptor(userId)                                │
│  - setProxy(userId, host, port)                          │
│  - setEnabled(userId, enabled)                           │
└────────────┬────────────────────────────────────────────┘
             │
             │ 使用
             ↓
┌─────────────────────────────────────────────────────────┐
│              ConfigRepository (Repository)               │
│  - saveConfig(userId, config)                           │
│  - loadConfig(userId)                                    │
│  - deleteConfig(userId)                                  │
│  - getAllConfigs()                                       │
└────────────┬────────────────────────────────────────────┘
             │
             │ 读写
             ↓
┌─────────────────────────────────────────────────────────┐
│            ConfigDataStore (DataStore)                   │
│  - DataStore<Preferences>                                │
│  - 序列化/反序列化                                        │
└────────────┬────────────────────────────────────────────┘
             │
             │ 持久化到
             ↓
┌─────────────────────────────────────────────────────────┐
│               Preferences (磁盘存储)                      │
│  - mock_config.preferences                               │
└─────────────────────────────────────────────────────────┘
```

#### 数据模型设计

**持久化数据类：**
```kotlin
package com.pankoku.appmock.config

import kotlinx.serialization.Serializable

@Serializable
data class UserMockConfig(
    val proxyHost: String = "",
    val proxyPort: Int = 0,
    val isEnabled: Boolean = false
) {
    fun isValid(): Boolean {
        return proxyHost.isNotBlank() &&
               proxyPort in 1..65535
    }
}
```

**内存配置类（保持不变）：**
```kotlin
package com.pankoku.appmock

data class MockConfig(
    var proxyHost: String = "",
    var proxyPort: Int = 0,
    var isEnabled: Boolean = false
)
```

#### 接口定义

**ConfigRepository 接口：**
```kotlin
package com.pankoku.appmock.config

import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    /**
     * 保存指定用户的配置
     */
    suspend fun saveConfig(userId: String, config: UserMockConfig)

    /**
     * 加载指定用户的配置
     */
    suspend fun loadConfig(userId: String): UserMockConfig?

    /**
     * 删除指定用户的配置
     */
    suspend fun deleteConfig(userId: String)

    /**
     * 清空所有配置
     */
    suspend fun clearAllConfigs()

    /**
     * 获取所有用户配置
     */
    suspend fun getAllConfigs(): Map<String, UserMockConfig>

    /**
     * 监听配置变化
     */
    fun observeConfig(userId: String): Flow<UserMockConfig?>

    /**
     * 监听所有配置变化
     */
    fun observeAllConfigs(): Flow<Map<String, UserMockConfig>>
}
```

#### DataStore 实现方案

**ConfigDataStore.kt：**
```kotlin
package com.pankoku.appmock.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.pankoku.appmock.config.UserMockConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// DataStore 扩展
val Context.mockDataStore: DataStore<Preferences> by
    preferencesDataStore(name = "mock_config")

// Preferences Key 定义
private object PreferencesKeys {
    val CONFIG_PREFIX = stringPreferencesKey("config_prefix")
    private const val CONFIG_KEY_TEMPLATE = "config_%s"

    fun getConfigKey(userId: String): Preferences.Key<String> {
        return stringPreferencesKey(String.format(CONFIG_KEY_TEMPLATE, userId))
    }
}

class ConfigDataStore(private val context: Context) {
    private val dataStore = context.mockDataStore
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * 保存配置
     */
    suspend fun saveConfig(userId: String, config: UserMockConfig) {
        dataStore.edit { preferences ->
            val jsonString = json.encodeToString(config)
            preferences[PreferencesKeys.getConfigKey(userId)] = jsonString
        }
    }

    /**
     * 加载配置
     */
    fun loadConfig(userId: String): Flow<UserMockConfig?> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[PreferencesKeys.getConfigKey(userId)]
            if (jsonString != null) {
                try {
                    json.decodeFromString<UserMockConfig>(jsonString)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }
    }

    /**
     * 删除配置
     */
    suspend fun deleteConfig(userId: String) {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.getConfigKey(userId))
        }
    }

    /**
     * 清空所有配置
     */
    suspend fun clearAllConfigs() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    /**
     * 获取所有配置
     */
    suspend fun getAllConfigs(): Map<String, UserMockConfig> {
        val result = mutableMapOf<String, UserMockConfig>()
        dataStore.data.collect { preferences ->
            preferences.asMap().forEach { (key, value) ->
                if (key.name.startsWith("config_")) {
                    val userId = key.name.removePrefix("config_")
                    try {
                        val config = json.decodeFromString<UserMockConfig>(value as String)
                        result[userId] = config
                    } catch (e: Exception) {
                        // 忽略解析失败的配置
                    }
                }
            }
        }
        return result
    }

    /**
     * 监听单个配置变化
     */
    fun observeConfig(userId: String): Flow<UserMockConfig?> {
        return loadConfig(userId)
    }

    /**
     * 监听所有配置变化
     */
    fun observeAllConfigs(): Flow<Map<String, UserMockConfig>> {
        return dataStore.data.map { preferences ->
            val result = mutableMapOf<String, UserMockConfig>()
            preferences.asMap().forEach { (key, value) ->
                if (key.name.startsWith("config_")) {
                    val userId = key.name.removePrefix("config_")
                    try {
                        val config = json.decodeFromString<UserMockConfig>(value as String)
                        result[userId] = config
                    } catch (e: Exception) {
                        // 忽略解析失败的配置
                    }
                }
            }
            result
        }
    }
}
```

**ConfigRepository 实现：**
```kotlin
package com.pankoku.appmock.persistence

import android.content.Context
import com.pankoku.appmock.config.ConfigRepository
import com.pankoku.appmock.config.UserMockConfig
import kotlinx.coroutines.flow.Flow

class ConfigRepositoryImpl(
    private val context: Context
) : ConfigRepository {
    private val dataStore = ConfigDataStore(context)

    override suspend fun saveConfig(userId: String, config: UserMockConfig) {
        dataStore.saveConfig(userId, config)
    }

    override suspend fun loadConfig(userId: String): UserMockConfig? {
        return dataStore.loadConfig(userId).collect { result ->
            return result
        }
        return null
    }

    override suspend fun deleteConfig(userId: String) {
        dataStore.deleteConfig(userId)
    }

    override suspend fun clearAllConfigs() {
        dataStore.clearAllConfigs()
    }

    override suspend fun getAllConfigs(): Map<String, UserMockConfig> {
        return dataStore.getAllConfigs()
    }

    override fun observeConfig(userId: String): Flow<UserMockConfig?> {
        return dataStore.observeConfig(userId)
    }

    override fun observeAllConfigs(): Flow<Map<String, UserMockConfig>> {
        return dataStore.observeAllConfigs()
    }
}
```

#### AppMock 集成方案

**修改后的 AppMock.kt：**
```kotlin
package com.pankoku.appmock

import android.content.Context
import com.pankoku.appmock.config.UserMockConfig
import com.pankoku.appmock.persistence.ConfigRepository
import com.pankoku.appmock.persistence.ConfigRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppMock private constructor(
    private val context: Context
) {
    private val configs = ConcurrentHashMap<String, MockConfig>()
    private val repository: ConfigRepository = ConfigRepositoryImpl(context)
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        // 异步加载所有配置
        loadAllConfigs()
    }

    /**
     * 从持久化存储加载所有配置
     */
    private fun loadAllConfigs() {
        scope.launch {
            val savedConfigs = repository.getAllConfigs()
            savedConfigs.forEach { (userId, userConfig) ->
                val config = configs.getOrPut(userId) { MockConfig() }
                config.proxyHost = userConfig.proxyHost
                config.proxyPort = userConfig.proxyPort
                config.isEnabled = userConfig.isEnabled
            }
        }
    }

    /**
     * 保存所有配置到持久化存储
     */
    suspend fun saveAllConfigs() {
        configs.forEach { (userId, config) ->
            val userConfig = UserMockConfig(
                proxyHost = config.proxyHost,
                proxyPort = config.proxyPort,
                isEnabled = config.isEnabled
            )
            repository.saveConfig(userId, userConfig)
        }
    }

    /**
     * 获取指定用户的配置，不存在则自动创建
     */
    fun config(userId: String): MockConfig {
        return configs.getOrPut(userId) { MockConfig() }
    }

    /**
     * 设置指定用户的代理服务器地址
     */
    fun setProxy(userId: String, host: String, port: Int): AppMock {
        config(userId).apply {
            this.proxyHost = host
            this.proxyPort = port
        }

        // 异步保存到持久化存储
        scope.launch {
            val config = config(userId)
            val userConfig = UserMockConfig(
                proxyHost = config.proxyHost,
                proxyPort = config.proxyPort,
                isEnabled = config.isEnabled
            )
            repository.saveConfig(userId, userConfig)
        }

        return this
    }

    /**
     * 设置指定用户是否启用代理拦截
     */
    fun setEnabled(userId: String, enabled: Boolean): AppMock {
        config(userId).isEnabled = enabled

        // 异步保存到持久化存储
        scope.launch {
            val config = config(userId)
            val userConfig = UserMockConfig(
                proxyHost = config.proxyHost,
                proxyPort = config.proxyPort,
                isEnabled = config.isEnabled
            )
            repository.saveConfig(userId, userConfig)
        }

        return this
    }

    /**
     * 创建代理拦截器实例，绑定到指定用户
     */
    fun newInterceptor(userId: String): ProxyInterceptor {
        return ProxyInterceptor(this, userId)
    }

    /**
     * 删除指定用户的配置
     */
    suspend fun deleteUserConfig(userId: String) {
        configs.remove(userId)
        repository.deleteConfig(userId)
    }

    /**
     * 清空所有用户配置
     */
    suspend fun clearAllUserConfigs() {
        configs.clear()
        repository.clearAllConfigs()
    }

    /**
     * 获取所有用户ID列表
     */
    fun getAllUserIds(): List<String> {
        return configs.keys.toList()
    }

    companion object {
        private var instance: AppMock? = null

        @JvmStatic
        fun getInstance(context: Context): AppMock {
            return instance ?: synchronized(this) {
                instance ?: AppMock(context.applicationContext).also {
                    instance = it
                }
            }
        }

        // 向后兼容的单例方法（不推荐使用）
        @Deprecated(
            message = "Use getInstance(Context) instead",
            replaceWith = ReplaceWith("AppMock.getInstance(context)")
        )
        @JvmField
        val instance: AppMock
            get() = throw IllegalStateException(
                "AppMock must be initialized with Context. " +
                "Use AppMock.getInstance(context) instead."
            )
    }
}
```

#### 初始化方案

**AppMockInitializer.kt：**
```kotlin
package com.pankoku.appmock

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppMockInitializer {
    private var isInitialized = false

    fun init(context: Context) {
        if (!isInitialized) {
            // 获取单例实例，触发初始化
            AppMock.getInstance(context)
            isInitialized = true
        }
    }

    /**
     * 在 Application 中初始化
     */
    fun initializeInApplication(application: android.app.Application) {
        init(application)

        // 预加载配置
        CoroutineScope(Dispatchers.IO).launch {
            AppMock.getInstance(application).saveAllConfigs()
        }
    }
}
```

**在 Application 中使用：**
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppMockInitializer.initializeInApplication(this)
    }
}
```

#### 文件结构变更

**新增文件：**
```
appmock/src/main/java/com/pankoku/appmock/
  ├── config/
  │   ├── UserMockConfig.kt          # 持久化数据类
  │   └── ConfigRepository.kt        # 配置仓储接口
  ├── persistence/
  │   ├── ConfigDataStore.kt         # DataStore 实现
  │   └── ConfigRepositoryImpl.kt    # Repository 实现
  └── AppMockInitializer.kt          # 初始化工具
```

**修改文件：**
```
appmock/src/main/java/com/pankoku/appmock/
  ├── AppMock.kt                     # 集成持久化
  └── MockConfig.kt                  # 保持不变
```

#### 依赖添加

**build.gradle.kts：**
```kotlin
dependencies {
    // ... 现有依赖

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}

plugins {
    // ... 现有插件
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}
```

#### 关键实现要点

**1. 协程集成**
- 使用 `CoroutineScope(Dispatchers.IO)` 管理 IO 操作
- 在应用启动时异步加载配置
- 配置保存时使用异步操作，不阻塞主线程

**2. 配置变更监听**
- 使用 `Flow` 监听配置变化
- 在需要时可以订阅配置变化通知
- 支持实时更新配置

**3. 数据一致性**
- 内存配置作为缓存
- 持久化配置作为数据源
- 设置时同步更新内存和持久化
- 启动时从持久化加载到内存

**4. 向后兼容**
- 提供新的初始化方法 `getInstance(Context)`
- 标记旧的单例方法为 `@Deprecated`
- 提供清晰的迁移指南

---

### 线程安全详细方案

#### 问题分析

当前 `AppMock.configs` 使用 `mutableMapOf`，在多线程环境下不安全：

```kotlin
// 现有代码（不安全）
private val configs = mutableMapOf<String, MockConfig>()

fun config(userId: String): MockConfig {
    return configs.getOrPut(userId) { MockConfig() }  // 非线程安全
}
```

#### 解决方案：ConcurrentHashMap

**实现方式：**
```kotlin
import java.util.concurrent.ConcurrentHashMap

class AppMock private constructor(
    private val context: Context
) {
    private val configs = ConcurrentHashMap<String, MockConfig>()

    fun config(userId: String): MockConfig {
        return configs.getOrPut(userId) { MockConfig() }
    }

    fun setProxy(userId: String, host: String, port: Int): AppMock {
        config(userId).apply {
            this.proxyHost = host
            this.proxyPort = port
        }
        return this
    }

    // ... 其他方法
}
```

**优势：**
- `ConcurrentHashMap` 的 `getOrPut` 是线程安全的
- 读操作不需要加锁，性能更好
- 写操作通过分段锁保证线程安全
- 适用于高并发读、低并发写的场景

#### 测试验证

**并发测试用例：**
```kotlin
@Test
fun `concurrent setProxy should be thread-safe`() = runBlocking {
    val userId = "test_user"
    val threads = 100
    val latch = CountDownLatch(threads)

    repeat(threads) {
        thread {
            appMock.setProxy(userId, "host_$it", 8080)
            latch.countDown()
        }
    }

    latch.await()

    // 验证配置一致性
    val config = appMock.config(userId)
    assertNotNull(config.proxyHost)
    assertTrue(config.proxyPort > 0)
}
```

---

### 异常处理详细方案

#### 问题分析

当前 `ProxyInterceptor` 没有异常处理：

```kotlin
// 现有代码（无异常处理）
override fun intercept(chain: Interceptor.Chain): Response {
    val original: Request = chain.request()
    val config = appMock.config(userId)

    if (!config.isEnabled) {
        return chain.proceed(original)
    }

    val newUrl: HttpUrl = original.url.newBuilder()
        .host(config.proxyHost)
        .port(config.proxyPort)
        .build()

    val proxyRequest: Request = original.newBuilder()
        .url(newUrl)
        .build()

    return chain.proceed(proxyRequest)  // 可能抛出异常
}
```

#### 解决方案：降级策略

**实现方式：**
```kotlin
package com.pankoku.appmock

import okhttp3.*
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ProxyInterceptor(
    private val appMock: AppMock,
    private val userId: String
) : Interceptor {

    companion object {
        private const val TAG = "ProxyInterceptor"
    }

    private var errorListener: ErrorListener? = null

    interface ErrorListener {
        fun onProxyError(
            userId: String,
            exception: Exception,
            originalRequest: Request
        )
    }

    fun setErrorListener(listener: ErrorListener) {
        this.errorListener = listener
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val config = appMock.config(userId)

        if (!config.isEnabled) {
            return chain.proceed(original)
        }

        return try {
            doIntercept(chain, original, config)
        } catch (e: Exception) {
            handleProxyError(chain, original, e)
        }
    }

    private fun doIntercept(
        chain: Interceptor.Chain,
        original: Request,
        config: MockConfig
    ): Response {
        val newUrl: HttpUrl = original.url.newBuilder()
            .host(config.proxyHost)
            .port(config.proxyPort)
            .build()

        val proxyRequest: Request = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(proxyRequest)
    }

    private fun handleProxyError(
        chain: Interceptor.Chain,
        original: Request,
        exception: Exception
    ): Response {
        // 记录错误日志
        when (exception) {
            is ConnectException -> {
                Log.e(TAG, "Proxy connection failed for user $userId", exception)
            }
            is SocketTimeoutException -> {
                Log.e(TAG, "Proxy timeout for user $userId", exception)
            }
            else -> {
                Log.e(TAG, "Proxy error for user $userId", exception)
            }
        }

        // 通知错误监听器
        errorListener?.onProxyError(userId, exception, original)

        // 降级到原始请求
        Log.w(TAG, "Fallback to original request for user $userId")
        return chain.proceed(original)
    }
}
```

#### 异常类型分类

| 异常类型 | 原因 | 处理策略 |
|---------|------|---------|
| `ConnectException` | 代理服务器连接失败 | 降级到原始请求 |
| `SocketTimeoutException` | 代理服务器响应超时 | 降级到原始请求 |
| `UnknownHostException` | 代理服务器地址无效 | 降级到原始请求 |
| `SSLException` | HTTPS 代理证书错误 | 降级到原始请求 |
| `IOException` | 其他 IO 错误 | 降级到原始请求 |
| `RuntimeException` | 配置错误等运行时异常 | 降级到原始请求 |

#### 错误回调使用示例

```kotlin
val interceptor = AppMock.getInstance(context)
    .newInterceptor(userId)
    .apply {
        setErrorListener(object : ProxyInterceptor.ErrorListener {
            override fun onProxyError(
                userId: String,
                exception: Exception,
                originalRequest: Request
            ) {
                // 处理代理错误
                when (exception) {
                    is ConnectException -> {
                        // 代理连接失败，可能需要通知用户或自动重试
                        showProxyUnavailableNotification(userId)
                    }
                }
            }
        })
    }
```

---

### 配置验证详细方案

#### 验证规则

```kotlin
package com.pankoku.appmock.config

data class ConfigValidationResult(
    val isValid: Boolean,
    val errors: List<ValidationError>
)

data class ValidationError(
    val field: String,
    val message: String
)

class ConfigValidator {

    fun validate(config: UserMockConfig): ConfigValidationResult {
        val errors = mutableListOf<ValidationError>()

        // 验证主机名
        if (config.proxyHost.isBlank()) {
            errors.add(
                ValidationError(
                    field = "proxyHost",
                    message = "Host cannot be blank"
                )
            )
        }

        // 验证端口号
        if (config.proxyPort < 1 || config.proxyPort > 65535) {
            errors.add(
                ValidationError(
                    field = "proxyPort",
                    message = "Port must be between 1 and 65535"
                )
            )
        }

        return ConfigValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    fun validateProxyHost(host: String): Boolean {
        return host.isNotBlank() &&
               host.matches(Regex("^[a-zA-Z0-9.-]+$"))
    }

    fun validateProxyPort(port: Int): Boolean {
        return port in 1..65535
    }
}
```

#### 在 AppMock 中集成验证

```kotlin
class AppMock private constructor(
    private val context: Context
) {
    private val validator = ConfigValidator()

    fun setProxy(userId: String, host: String, port: Int): AppMock {
        // 验证参数
        require(validator.validateProxyHost(host)) {
            "Invalid proxy host: $host"
        }
        require(validator.validateProxyPort(port)) {
            "Invalid proxy port: $port (must be 1-65535)"
        }

        config(userId).apply {
            this.proxyHost = host
            this.proxyPort = port
        }

        return this
    }

    fun validateConfig(userId: String): ConfigValidationResult {
        val config = config(userId)
        val userConfig = UserMockConfig(
            proxyHost = config.proxyHost,
            proxyPort = config.proxyPort,
            isEnabled = config.isEnabled
        )
        return validator.validate(userConfig)
    }
}
```

---

## 风险评估

### 高风险项

#### 1. 配置持久化性能影响

**风险描述：**
频繁的磁盘 I/O 可能影响应用性能。

**影响程度：** 🔴 高

**缓解措施：**
- ✅ 使用异步 API（DataStore），不阻塞主线程
- ✅ 在内存中缓存配置，减少磁盘读取
- ✅ 批量写入策略，合并频繁的配置更新
- ✅ 使用 IO 线程池，避免阻塞网络请求线程

**测试验证：**
- 压力测试：模拟高频配置更新场景
- 性能监控：测量磁盘 I/O 对请求耗时的影响

---

#### 2. 向后兼容性

**风险描述：**
API 变更可能影响现有用户。

**影响程度：** 🟡 中

**缓解措施：**
- ✅ 提供 `@Deprecated` 标记和迁移指南
- ✅ 保持旧的 API 行为，标记为不推荐使用
- ✅ 提供清晰的错误信息，引导用户使用新 API
- ✅ 提供兼容模式，支持渐进式迁移

**迁移策略：**
```kotlin
// 旧方式（不推荐，仍然可用但会抛出异常）
@Deprecated(
    message = "Use getInstance(Context) instead",
    replaceWith = ReplaceWith("AppMock.getInstance(context)"),
    level = DeprecationLevel.ERROR
)
@JvmField
val instance: AppMock
    get() = throw IllegalStateException("...")

// 新方式（推荐）
AppMock.getInstance(context).setProxy(userId, "host", 8080)
```

---

### 中风险项

#### 3. 多线程并发测试

**风险描述：**
并发问题难以复现和调试。

**影响程度：** 🟡 中

**缓解措施：**
- ✅ 使用 `ConcurrentHashMap` 保证基本线程安全
- ✅ 增加自动化并发测试用例
- ✅ 使用压力测试工具模拟高并发场景
- ✅ 代码审查，检查潜在的竞态条件

**测试策略：**
- 单元测试：使用多线程并发调用 API
- 集成测试：模拟高并发网络请求场景
- 压力测试：使用 JMeter 或类似工具进行压力测试

---

## 参考资料

### 官方文档
- [Android DataStore 官方文档](https://developer.android.com/topic/libraries/architecture/datastore)
- [OkHttp Interceptor 最佳实践](https://square.github.io/okhttp/interceptors/)
- [Kotlin Coroutines 指南](https://kotlinlang.org/docs/coroutines-guide.html)
- [Kotlin Serialization 文档](https://kotlinlang.org/docs/serialization.html)

### 最佳实践
- [Android Jetpack 最佳实践](https://developer.android.com/jetpack/guide)
- [OkHttp 官方文档](https://square.github.io/okhttp/)
- [Coroutines 协程最佳实践](https://developer.android.com/kotlin/coroutines/coroutines-best-practices)

### 相关库
- [AndroidX DataStore](https://developer.android.com/jetpack/androidx/releases/datastore)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [OkHttp](https://github.com/square/okhttp)
- [Timber 日志库](https://github.com/JakeWharton/timber)

---

## 版本历史

| 版本 | 日期 | 说明 |
|------|------|------|
| 1.0.0 | 2026-08-02 | 初始版本，定义技术方案 |