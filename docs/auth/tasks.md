# 认证与账户任务 (Auth & Account)

## 当前状态: ✅ 已完成核心迁移 (等待 Review)

## 任务进展
1. **[紧急] Vault 接入**: 
    - [x] 迁移 `UserCenterImpl` 至 Kotlin 以适配协程 API。
    - [x] 移除 `SecureSharedPreference`。
    - [x] 使用 `GHStorage.getSecureStorage()` 存储 `auth_key` 和 `user_detail`。
    - [x] 确保用户凭证在本地物理加密 (基于 MMKV AES-256-CFB)。
2. **[关键] Token 机制**:
    - [ ] 实现 Token 自动刷新逻辑。
    - [ ] 完善 401 登录失效全局拦截与重定向。
3. **[同步] 逻辑稳固**:
    - [x] 处理登录/登出的异步存储调用。
    - [ ] 补齐 UserCenter 的单元测试。
4. **[完成] 语言迁移**:
    - [x] 将 `UserCenterImpl.java` 迁移至 Kotlin。

## 交付产物
- [任务完成简报](review_reports/summary.md)
- `app/src/main/kotlin/com/github/account/UserCenterImpl.kt`
- `app/src/main/kotlin/com/github/account/IUserCenter.kt`
