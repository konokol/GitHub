# 认证与账户任务 (Auth & Account)

## 当前状态: 🚧 迁移与 Vault 接入中

## 优先任务清单 (按紧急程度排序)
1. **[紧急] Vault 接入**: 
    - [x] 迁移 `UserCenterImpl` 至 Kotlin 以适配协程 API。
    - [/] 移除 `SecureSharedPreference`。
    - [/] 使用 `GHStorage.getSecureStorage()` 存储 `auth_key` 和 `user_detail`。
    - [ ] 确保用户凭证在本地物理加密。
2. **[关键] Token 机制**:
    - [ ] 实现 Token 自动刷新逻辑。
    - [ ] 完善 401 登录失效全局拦截与重定向。
3. **[同步] 逻辑稳固**:
    - [x] 处理登录/登出的异步存储调用。
    - [ ] 补齐 UserCenter 的单元测试。
4. **[完成] 语言迁移**:
    - [x] 将 `UserCenterImpl.java` 迁移至 Kotlin。

## 文件清单
- `app/src/main/kotlin/com/github/account/UserCenterImpl.kt` (New)
- `app/src/main/kotlin/com/github/account/IUserCenter.kt` (New)
- `app/src/main/kotlin/com/github/app/GHStorage.kt` -> 存储依赖
