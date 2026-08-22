# AppMock 模块改进计划

## 项目概述

AppMock 是一个 OkHttp 网络请求拦截器库，支持按用户标识将网络请求转发到不同的代理服务器。当前版本实现了基本功能，但在生产环境稳定性、可维护性和用户体验方面还有改进空间。

## 当前架构分析

### 现有组件
- **AppMock.kt** - 门面类，管理多用户配置和拦截器创建
- **MockConfig.kt** - 单用户配置数据类
- **ProxyInterceptor.kt** - OkHttp 拦截器，实现请求转发

### 现有优势
- ✅ 职责分离清晰
- ✅ 支持多用户配置隔离
- ✅ 提供链式调用API
- ✅ 包含基础单元测试

---

## 需要改进的问题清单

### 🔴 高优先级（影响稳定性）

#### 1. 配置持久化 ⭐ **当前最需要改进**
**问题：** 配置只存储在内存中，应用重启后会丢失，需要重新设置。

**影响：**
- 用户需要在每次应用启动后重新配置代理
- 无法实现自动化的持续测试
- 用户体验差，需要手动管理配置

**解决方案：**
- 集成 SharedPreferences 或 DataStore 进行配置持久化
- 提供配置导入/导出功能
- 支持默认配置加载

**工作量：** 2-3天

---

#### 2. 线程安全性
**问题：** `AppMock.configs` 使用 `mutableMapOf`，在多线程环境下存在数据竞争风险。

**影响：**
- 并发设置配置可能导致数据丢失或覆盖
- 在多线程网络请求中可能出现不可预期行为

**解决方案：**
- 使用 `ConcurrentHashMap` 替代 `mutableMapOf`
- 或使用 `synchronized` 保护关键操作

**工作量：** 0.5天

---

#### 3. 异常处理
**问题：** `ProxyInterceptor` 没有异常处理，代理服务器不可用会导致请求失败。

**影响：**
- 代理连接失败会导致整个请求链中断
- 没有降级机制，影响应用稳定性
- 缺少错误信息，难以排查问题

**解决方案：**
- 添加 try-catch 异常捕获
- 提供降级策略（代理失败时走原始请求）
- 添加错误回调机制

**工作量：** 1天

---

#### 4. 配置验证
**问题：** 没有验证代理服务器地址的有效性。

**影响：**
- 可以设置空的主机名
- 可以设置无效的端口号（< 0 或 > 65535）
- 只在请求时才发现配置错误

**解决方案：**
- 添加参数校验
- 提供配置验证接口
- 添加配置状态查询

**工作量：** 0.5天

---

#### 5. 日志记录
**问题：** 缺少日志记录，难以调试和监控代理行为。

**影响：**
- 无法追踪请求是否被代理
- 难以排查代理相关问题
- 缺少使用统计信息

**解决方案：**
- 集成日志框架（如 Timber）
- 记录关键操作和状态变化
- 提供日志开关配置

**工作量：** 0.5天

---

### 🟡 中优先级（提升可用性）

#### 6. 配置管理 API
**问题：** 缺少完整的配置管理功能。

**缺失功能：**
- 删除指定用户配置
- 清空所有配置
- 获取所有用户列表
- 配置查询和统计

**工作量：** 1天

---

#### 7. 代码质量
**问题：** 存在代码风格问题。

- `MockConfig.kt` 缺少尾随逗号
- 需要添加更多 KDoc 文档
- 需要添加使用示例

**工作量：** 0.5天

---

#### 8. 测试覆盖
**问题：** 测试覆盖不够全面。

**需要补充的测试：**
- 边界条件测试
- 异常情况测试
- 并发测试
- 配置验证测试

**工作量：** 1天

---

### 🟢 低优先级（增强功能）

#### 9. 高级代理配置
- 支持 HTTPS 代理
- 支持代理认证
- 支持超时配置
- 支持白名单/黑名单

**工作量：** 2-3天

---

#### 10. 性能监控
- 请求耗时统计
- 代理成功率统计
- 配置使用分析

**工作量：** 1-2天

---

## 实施计划

### 🚀 第一阶段：核心稳定性（1周）

#### 目标
解决影响生产环境稳定性的关键问题，建立可靠的基础架构。

#### 任务列表
- [ ] **配置持久化**（重点）
  - [ ] 设计持久化存储方案
  - [ ] 实现配置保存和加载
  - [ ] 添加配置变更监听
  - [ ] 编写持久化单元测试
  - [ ] 更新集成文档

- [ ] **线程安全**
  - [ ] 使用 ConcurrentHashMap 替换 mutableMapOf
  - [ ] 添加并发测试用例
  - [ ] 验证线程安全性

- [ ] **异常处理**
  - [ ] 添加 try-catch 异常捕获
  - [ ] 实现降级策略
  - [ ] 添加错误回调接口
  - [ ] 编写异常处理测试

- [ ] **配置验证**
  - [ ] 添加参数校验逻辑
  - [ ] 实现配置验证接口
  - [ ] 添加验证失败处理

#### 验收标准
- ✅ 应用重启后配置能够恢复
- ✅ 多线程环境下配置不会出错
- ✅ 代理失败时能够降级到原始请求
- ✅ 无效配置能够被及时拒绝

---

### 🔧 第二阶段：可用性提升（3天）

#### 目标
提升库的易用性和可维护性。

#### 任务列表
- [ ] **配置管理 API**
  - [ ] 实现删除用户配置方法
  - [ ] 实现清空配置方法
  - [ ] 实现用户列表查询方法

- [ ] **日志记录**
  - [ ] 集成日志框架
  - [ ] 添加关键操作日志
  - [ ] 提供日志开关

- [ ] **代码质量**
  - [ ] 修复代码风格问题
  - [ ] 完善 KDoc 文档
  - [ ] 添加使用示例

- [ ] **测试补充**
  - [ ] 补充边界条件测试
  - [ ] 补充异常情况测试
  - [ ] 补充并发测试

#### 验收标准
- ✅ 配置管理功能完整
- ✅ 关键操作有日志记录
- ✅ 代码质量检查通过
- ✅ 测试覆盖率 > 80%

---

### 🌟 第三阶段：功能增强（可选，按需进行）

#### 任务列表
- [ ] **高级代理配置**
- [ ] **性能监控**

---

## 技术方案设计

### 配置持久化方案设计

#### 方案选择对比

| 方案 | 优点 | 缺点 | 推荐度 |
|------|------|------|--------|
| SharedPreferences | 简单易用，无需额外依赖 | 同步操作，性能较差 | ⭐⭐⭐ |
| DataStore | 异步操作，性能好，类型安全 | API 相对复杂 | ⭐⭐⭐⭐⭐ |
| Room | 功能强大，支持复杂查询 | 引入重量级依赖 | ⭐⭐ |

#### 推荐方案：DataStore

**优势：**
1. Google 推荐的现代化解决方案
2. 异步 API，不阻塞主线程
3. 类型安全，编译时检查
4. 事务性更新，保证数据一致性
5. 自动处理数据迁移

#### 实现架构

```kotlin
// 1. 定义数据类
data class UserMockConfig(
    val proxyHost: String = "",
    val proxyPort: Int = 0,
    val isEnabled: Boolean = false
)

// 2. 创建 DataStore 扩展
val Context.mockDataStore: DataStore<Preferences> by preferencesDataStore(name = "mock_config")

// 3. 实现 ConfigRepository
class ConfigRepository(private val context: Context) {
    private val dataStore = context.mockDataStore

    suspend fun saveConfig(userId: String, config: UserMockConfig) { ... }
    suspend fun loadConfig(userId: String): UserMockConfig? { ... }
    suspend fun deleteConfig(userId: String) { ... }
    suspend fun getAllConfigs(): Map<String, UserMockConfig> { ... }
}

// 4. 修改 AppMock 类
class AppMock private constructor(
    private val context: Context
) {
    private val repository = ConfigRepository(context)

    suspend fun loadAllConfigs() { ... }
    suspend fun saveAllConfigs() { ... }
}
```

#### 关键实现要点

1. **协程集成**
   - 使用 `lifecycleScope` 或 `viewModelScope` 管理协程
   - 在应用启动时异步加载配置

2. **配置变更监听**
   - 使用 `Flow` 监听配置变化
   - 自动同步内存配置和持久化配置

3. **数据迁移**
   - 如果从 SharedPreferences 迁移，提供迁移策略
   - 版本管理，支持未来数据结构变更

4. **初始化时机**
   - 在 Application 的 `onCreate` 中初始化
   - 首次使用时懒加载

#### 文件变更计划

**新增文件：**
```
appmock/src/main/java/com/pankoku/appmock/
  ├── config/
  │   ├── UserMockConfig.kt          # 持久化数据类
  │   └── ConfigRepository.kt        # 配置仓储接口
  ├── persistence/
  │   ├── ConfigDataStore.kt         # DataStore 实现
  │   └── ConfigSerializer.kt        # 数据序列化
  └── AppMockInitializer.kt          # 初始化类
```

**修改文件：**
```
appmock/src/main/java/com/pankoku/appmock/
  ├── AppMock.kt                     # 集成持久化
  └── MockConfig.kt                  # 保持不变（内存模型）
```

**新增依赖：**
```kotlin
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

---

### 线程安全方案

#### 实现方式
```kotlin
class AppMock private constructor() {
    private val configs = ConcurrentHashMap<String, MockConfig>()

    fun config(userId: String): MockConfig {
        return configs.getOrPut(userId) { MockConfig() }
    }
}
```

---

### 异常处理方案

#### 降级策略设计
```kotlin
override fun intercept(chain: Interceptor.Chain): Response {
    return try {
        doIntercept(chain)
    } catch (e: Exception) {
        // 记录错误日志
        Log.e(TAG, "Proxy failed, fallback to original request", e)
        // 降级到原始请求
        chain.proceed(chain.request())
    }
}

private fun doIntercept(chain: Interceptor.Chain): Response {
    // 原有逻辑
}
```

---

## 时间估算

| 阶段 | 工作量 | 完成时间 |
|------|--------|----------|
| 第一阶段：核心稳定性 | 5-7天 | 第1-2周 |
| 第二阶段：可用性提升 | 3天 | 第2-3周 |
| 第三阶段：功能增强 | 可选 | 按需安排 |

---

## 风险评估

### 高风险项
1. **配置持久化性能影响**
   - 风险：频繁的磁盘 I/O 可能影响性能
   - 缓解：使用缓存 + 批量写入策略

2. **向后兼容性**
   - 风险：API 变更可能影响现有用户
   - 缓解：提供迁移指南和兼容层

### 中风险项
3. **多线程并发测试**
   - 风险：并发问题难以复现和调试
   - 缓解：增加自动化并发测试用例

---

## 成功指标

- ✅ 配置持久化成功率 > 99.9%
- ✅ 多线程环境无数据竞争
- ✅ 代理失败时降级成功率 100%
- ✅ 代码测试覆盖率 > 80%
- ✅ 无编译警告和严重代码质量问题

---

## 后续维护

### 持续改进
- 监控生产环境使用情况
- 收集用户反馈
- 定期更新依赖版本
- 持续优化性能

### 版本规划
- v2.0.0：核心稳定性改进（本计划）
- v2.1.0：高级代理配置
- v2.2.0：性能监控和分析

---

## 参考资料

- [Android DataStore 官方文档](https://developer.android.com/topic/libraries/architecture/datastore)
- [OkHttp Interceptor 最佳实践](https://square.github.io/okhttp/interceptors/)
- [Kotlin Coroutines 指南](https://kotlinlang.org/docs/coroutines-guide.html)