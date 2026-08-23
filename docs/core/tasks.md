# 核心基础设施任务 (Core & Infrastructure)

## 当前状态: 🚧 架构完善中

## 任务清单
- [ ] **Vault 遗留技术债**:
    - [ ] **线程安全**: 将 `MMKVStorage` 和 `GHStorage` 中的 `HashMap` 替换为 `ConcurrentHashMap`。
    - [ ] **DataStore 冲突**: 修复 `DataStore` 委托可能导致的实例冲突崩溃。
    - [ ] **监听优化**: 优化 MMKV `watch` 对复杂对象的反序列化性能和正确性。
- [ ] **网络层 (HttpClient) 升级**:
    - [ ] 支持 Kotlin 协程 (suspend functions)。
    - [ ] 接入更现代的错误处理机制 (Result<T>)。
- [ ] **初始化流水线 (Init)**:
    - [ ] 优化 `AsyncAppInitializer`，支持更精细的初始化阶段控制。
- [ ] **路由系统 (Router)**:
    - [ ] 将简单的 URL 路由升级为类型安全的导航系统或成熟的路由框架。
- [ ] **全局 UI 基础**:
    - [ ] 现代化的 Loading 和 EmptyView 状态管理。

## 关联模块
- `:app` (core, common 包)
- `:library`
- `:vault`
- `:vault-datastore`
- `:vault-mmkv`
