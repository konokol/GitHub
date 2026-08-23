# 认证与账户任务 (Auth & Account)

## 当前状态: 🚧 迁移中

## 任务清单
- [ ] **Kotlin 迁移**: 将 `UserCenterImpl.java` 转换为 `.kt`。
- [ ] **Vault 接入**: 
    - [ ] 移除 `SecureSharedPreference`。
    - [ ] 使用 `GHStorage.getSecureStorage()` 存储 `auth_key` 和 `user_detail`。
- [ ] **异步逻辑处理**:
    - [ ] 在 `init` 时异步加载用户信息。
    - [ ] 处理 `put` 和 `remove` 的协程调用。
- [ ] **Token 机制**:
    - [ ] 实现 Token 自动刷新逻辑。
    - [ ] 完善 401 登录失效拦截。

## 文件清单
- `app/src/main/java/com/github/account/UserCenterImpl.java` -> 迁移目标
- `app/src/main/kotlin/com/github/app/GHStorage.kt` -> 依赖项
