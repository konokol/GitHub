# 项目核心组件 Kotlin 迁移及 GHStorage 接入计划

## 目标
1. 将 `app` 模块的核心组件（`GitHub`, `AppModule`, `AppComponent`）从 Java 迁移到 Kotlin。
2. 将 `UserCenterImpl` 从 Java 迁移到 Kotlin，并将其持久化方案从 `SharedPreferences`/`SecureSharedPreference` 替换为 `GHStorage`。
3. 遵循项目最新的编码规约（禁止新建 Java 文件，Kotlin 优先）。

## 拟议变更

### 1. 核心 DI 组件迁移

#### [MODIFY] [GitHub.kt](file:///Users/lijun/Projects/GitHub/app/src/main/kotlin/com/github/GitHub.kt) [NEW]
- 将 `GitHub.java` 转换为 `GitHub.kt`。
- 使用 Kotlin 单例模式（Object）或伴生对象实现。

#### [MODIFY] [AppModule.kt](file:///Users/lijun/Projects/GitHub/app/src/main/kotlin/com/github/core/AppModule.kt) [NEW]
- 将 `AppModule.java` 转换为 `AppModule.kt`。
- 更新 Dagger 相关注解。

#### [MODIFY] [AppComponent.kt](file:///Users/lijun/Projects/GitHub/app/src/main/kotlin/com/github/core/AppComponent.kt) [NEW]
- 将 `AppComponent.java` 转换为 `AppComponent.kt`。

### 2. 用户中心组件迁移及存储升级

#### [MODIFY] [UserCenterImpl.kt](file:///Users/lijun/Projects/GitHub/app/src/main/kotlin/com/github/account/UserCenterImpl.kt) [NEW]
- 将 `UserCenterImpl.java` 转换为 `UserCenterImpl.kt`。
- 将 `SharedPreferences` 替换为 `GHStorage.getSecureStorage()`。
- 处理 `GHStorage` 的异步调用（put/get/remove）。

### 3. 文件清理

#### [DELETE] [GitHub.java](file:///Users/lijun/Projects/GitHub/app/src/main/java/com/github/GitHub.java)
#### [DELETE] [AppModule.java](file:///Users/lijun/Projects/GitHub/app/src/main/java/com/github/core/AppModule.java)
#### [DELETE] [AppComponent.java](file:///Users/lijun/Projects/GitHub/app/src/main/java/com/github/core/AppComponent.java)
#### [DELETE] [UserCenterImpl.java](file:///Users/lijun/Projects/GitHub/app/src/main/java/com/github/account/UserCenterImpl.java)

## 验证计划

### 自动化测试
- 运行现有的单元测试，确保用户中心逻辑正确。
- 编写新的测试用例验证 `GHStorage` 在 `UserCenterImpl` 中的集成。

### 手动验证
- 验证登录/注销流程是否正常。
- 检查应用重启后用户信息是否能正确恢复。
