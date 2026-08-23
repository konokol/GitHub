# GHStorage (Vault) 实施进度

## 当前状态：🚧 重构完成 (漫威主题包名替换 + GHStorage 命名恢复)

开始时间：2026-08-07
最近更新：2026-08-22

## 整体进度

- [x] 1. 技术方案设计完成 (Marvel - Vault)
- [x] 2. 项目结构重构 (vault, vault-datastore, vault-mmkv)
- [x] 3. 包名迁移 (`com.pancoku.vault`)
- [x] 4. 核心接口模块实现 (storage/vault)
- [x] 5. DataStore 实现模块 (vault-datastore)
- [x] 6. MMKV 实现模块 (vault-mmkv)
- [/] 7. 测试编写 (正在修复加密逻辑与稳定性)
- [x] 8. 文档完善与 AI 规约建立
- [ ] 9. UserCenterImpl 迁移 (核心业务接入)

## 详细进度

### Phase 1: 核心接口模块 (vault) ✅
- [x] 设计 KVStorage 接口 (支持 Flow 监听)
- [x] 设计 KVStorageFactory 接口
- [x] 定义数据模型与统一异常体系
- [x] 物理更名并同步包名 `com.pancoku.vault`

### Phase 2: DataStore 实现 (vault-datastore) ✅
- [x] 实现 DataStoreStorage (基于 Preferences DataStore)
- [x] **[稳定性修复]** 引入 `DataStoreManager` 解决重复初始化崩溃风险
- [x] 支持基本类型与复杂对象的 JSON 序列化

### Phase 3: MMKV 实现 (vault-mmkv) ✅
- [x] **[核心修复]** 实现基于 AndroidKeyStore 硬件隔离的真实加密 (`MMKVCryptKeyProvider`)
- [x] 改造 MMKVStorage，支持 AES-256-CFB 真加密
- [x] 实现初始化幂等化与线程安全

### Phase 4: App 模块集成与 AI 规约 ✅
- [x] 恢复 App 模块 Dagger/kapt 编译链路
- [x] 创建 `GHStorage.kt` 作为 App 层统一入口
- [x] 建立全平台 AI 规约 (`.ai/`, `.cursorrules`, `CLAUDE.md`)
- [x] 完善 Android 备份排除规则 (防止跨设备恢复解密失败)

## 已完成的更名对照

| 项目 | 最终名称 | 包名/路径 |
| :--- | :--- | :--- |
| 核心模块 | **vault** | `com.pancoku.vault` |
| DataStore 实现 | **vault-datastore** | `com.pancoku.vault.datastore` |
| MMKV 实现 | **vault-mmkv** | `com.pancoku.vault.mmkv` |
| App 层类名 | **GHStorage** | `app/src/main/kotlin/com/github/app/GHStorage.kt` |

## 技术难点

### 已解决
- [x] **Dagger 编译断裂**: 通过对齐 Kotlin 版本与配置 kapt 解决。
- [x] **MMKV 假加密**: 通过 KeyStore 包裹密钥方案实现真加密。
- [x] **DataStore 实例冲突**: 通过单例 Manager 统一管理实例。
- [x] **AI 工具同步**: 通过 Bridge Files 实现多工具规约共享。

### 待解决
- [ ] **复杂对象监听**: MMKV `watch` 的序列化性能优化。
- [ ] **跨模块依赖暴露**: 考虑将 `implementation` 改为 `api` 以简化调用。

## 下一步计划

1. **UserCenterImpl 迁移**：将核心业务的用户中心转为 Kotlin，并正式接入 `GHStorage.getSecureStorage()`。
2. **编写单元测试**：针对 MMKV 的真加密逻辑编写验证用例。
3. **性能基准测试**：对比 DataStore 与 MMKV 在大数据量下的读写延迟。

---
最后更新时间：2026-08-22 19:48
