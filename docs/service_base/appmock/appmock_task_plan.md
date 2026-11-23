# AppMock 客户端 SDK - 实施任务计划

## 📋 说明

本文档记录本项目中 **Android 客户端 SDK (`:appmock` 模块)** 的具体实施计划与 Task 清单。

**关联方案：** [AppMock 三端系统技术方案](appmock_technical_design.md)

---

## 实施任务清单 (Client SDK Implementation)

### 阶段一：设备 ID 与核心并发安全 🚧
- [ ] **1.1 设备 UUID 生成与持久化**
  - [ ] App 首次启动时生成唯一的 `deviceId`（UUID），存入 `GHStorage` / DataStore。
  - [ ] 提供 `appMock.deviceId` 全局只读属性。
- [ ] **1.2 数据结构并发保护**
  - [ ] 将 `AppMock.configs` 修改为并发安全的结构（`ConcurrentHashMap`）。
  - [ ] 确保在 OkHttp 异步线程池并发访问时读写原子性。
- [ ] **1.3 配置合法性校验**
  - [ ] 校验 `proxyHost` 不为空且格式合法。
  - [ ] 校验 `proxyPort` 必须在 1 ~ 65535 范围内。

---

### 阶段二：智能双 Header 与重定向拦截器 🚧
- [ ] **2.1 智能双标 Header 附带**
  - [ ] `ProxyInterceptor` 请求拦截时自动带入 Header `X-AppMock-DeviceId`（必带）。
  - [ ] 用户已登录时同时附带 Header `X-AppMock-UserId`；未登录时留空。
- [ ] **2.2 代理连接失败平滑降级 (Fallback Control)**
  - [ ] 在 `ProxyInterceptor.intercept` 中捕获 `ConnectException` / `SocketTimeoutException` 等代理异常。
  - [ ] 代理连接失败时，自动平滑降级调用 `chain.proceed(originalRequest)` 直连真实 GitHub REST API。
  - [ ] 输出 Logger 警告日志与 `ErrorListener` 回调。

---

### 阶段三：配置本地持久化 (GHStorage / DataStore) 🚧
- [ ] **3.1 序列化模型与仓储**
  - [ ] 创建 `UserMockConfig.kt` 序列化数据类（含 `proxyHost`, `proxyPort`, `deviceId`, `userId`, `isEnabled`）。
  - [ ] 创建 `ConfigRepository` 接口与其 DataStore / GHStorage 实现类。
- [ ] **3.2 AppMock 单例持久化集成**
  - [ ] 重构 `AppMock.getInstance(context)` 支持 Context 传入。
  - [ ] 启动时异步从本地加载历史代理配置、`deviceId` 与激活账号。
  - [ ] 变更代理 Host/Port 或使能状态时同步持久化落盘。

---

### 阶段四：账号注册与单元测试 ⏳
- [ ] **4.1 客户端注册接口**
  - [ ] 实现 `registerClient()` 客户端注册上报方法，传递 `deviceId`、`userId`、客户端版本号与设备型号。
- [ ] **4.2 单元测试与质量验证**
  - [ ] 编写 `ProxyInterceptorTest` 验证智能双 Header 附带与异常降级逻辑。
  - [ ] 编写 `ConfigRepositoryTest` 验证配置与 `deviceId` 持久化恢复。
  - [ ] 单元测试覆盖率 > 80%。
