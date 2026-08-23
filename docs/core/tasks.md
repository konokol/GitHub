# 核心基础设施任务 (Core & Infrastructure)

## 当前状态: 🚧 支撑业务开发中

## 优先任务清单
1. **[支撑] Vault 稳定性加固**:
    - 将 `GHStorage` 等处的 `HashMap` 升级为 `ConcurrentHashMap`，确保多线程业务存取安全。
    - 验证 `DataStore` 在复杂业务场景下的稳定性。
2. **[支撑] 网络层协程化**:
    - 升级 `HttpClient` 支持 `suspend` 挂起函数，方便后续业务模块快速开发。
3. **[同步] 架构规范**:
    - 建立统一的 `Result<T>` 业务返回规范。

## 后续优化 (低优先级)
- 优化初始化流水线细节。
- 升级路由系统。
