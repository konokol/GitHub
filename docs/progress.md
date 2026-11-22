# GitHub App 项目总体进展

## 1. 项目总目标
**补齐核心基础功能，完成一个功能完备、测试充分的 GitHub Android 客户端。**
- **当前重点**: 深入推进 **Feed 动态流 (FEED-001)**，优化事件渲染、分页加载与下拉刷新体验。
- **质量保证**: 实现 80% 以上的代码测试覆盖率。
- **架构支撑**: 基于三层架构（业务层 -> 基础业务层 -> 基础服务层）演进。

---

## 2. 架构分层与模块进度

```
项目总体进度 (三层架构)
├── 1. 业务层 (Business / Feature Layer)
│   ├── feed (动态流)                    [40% 🚧] <-- 当前开发重点 (FEED-001)
│   ├── repository (仓库浏览与源码)      [10% ⏳]
│   ├── issue_pr (Issue & PR)           [ 5% ⏳]
│   ├── notification (通知中心)          [ 5% ⏳]
│   └── profile (个人/组织主页)           [10% ⏳]
├── 2. 基础业务层 (Foundational Business Layer)
│   ├── account (认证与用户中心)          [85% ✅] (ACCOUNT-001)
│   ├── web (Web 通用容器与页面)          [60% 🚧]
│   ├── design_library (UI 组件库 :library) [90% ✅]
│   └── appmock (Mock 服务 :appmock)     [80% ✅]
└── 3. 基础服务层 (Base Service Layer)
    ├── vault (Vault 加密存储系统)       [95% ✅]
    ├── network (网络 API 服务)          [70% 🚧]
    ├── startup (应用启动流水线)         [85% ✅]
    ├── di (依赖注入服务 Dagger)         [75% 🚧]
    └── testing (测试与质量基础设施)     [ 5% ⏳]
```

---

## 3. 详细模块进展分解

### 层级 1：业务层 (`docs/business/`)

#### 1.1 动态流 (`feed`) | 进度: 40% 🚧 *(当前开发重点 FEED-001)*
- [x] Feed 列表基础框架 (MVP): `FeedFragment`, `FeedPresenter`, `FeedListAdapter`
- [x] 支持渲染 `PushEvent`, `DeleteEvent`, `CreateEvent`, `WatchEvent` 等基础事件类型
- [ ] **[重点]** 分页加载与下拉刷新体验优化
- [ ] **[增强]** 扩展 `ForkEvent`, `IssueCommentEvent`, `PullRequestEvent`, `ReleaseEvent` 渲染与卡片交互

#### 1.2 仓库浏览与源码 (`repository`) | 进度: 10% ⏳
- [x] 基础数据模型 (`Repository.java`)
- [ ] 仓库详情页实现 (Info, Code, Commits, Releases)
- [ ] 源码树导航与代码查看器
- [ ] 我的仓库列表与搜索结果展示

#### 1.3 Issue 与 Pull Request (`issue_pr`) | 进度: 5% ⏳
- [x] 基础数据模型 (`Issue.java`)
- [ ] Issue/PR 列表与详情页面
- [ ] 评论列表展示与发送回复功能

#### 1.4 通知中心 (`notification`) | 进度: 5% ⏳
- [x] 导航入口与占位 Fragment (`NotificationFragment`)
- [ ] 通知消息列表展示与已读/删除操作

#### 1.5 个人与组织主页 (`profile`) | 进度: 10% ⏳
- [x] 侧边栏 Profile 简要信息展示
- [ ] 个人主页与组织主页详细信息与头像预览

---

### 层级 2：基础业务层 (`docs/business_base/`)

#### 2.1 认证与用户中心 (`account`) | 进度: 85% ✅
- [x] GitHub OAuth 2.0 授权流程实现
- [x] 登录、登出、闪屏页 (Splash)
- [x] 登录凭证安全接入 Vault (ACCOUNT-001) - [详情](business_base/account/reports/vault_integration_walkthrough.md)
- [x] 完善登录过期处理与 401 自动登出机制
- [ ] **[下一步]** 完善 Token 刷新机制 (如果 GitHub API 需要)

#### 2.2 Web 通用容器与页面 (`web`) | 进度: 60% 🚧
- [x] 集成 `BifrostWebView` 控件与 `WebActivity` 通用承载页面
- [ ] 网页分享功能 (`WebActivity`)

#### 2.3 UI 组件库 (`design_library` - `:library`) | 进度: 90% ✅
- [x] `SwipeRefreshRecyclerView` 下拉刷新与下拉加载组件
- [x] 状态布局 (`EmptyView`, `LoadingView`) 与 Indicator
- [x] Logger 全局日志框架

#### 2.4 Mock 测试与代理服务 (`appmock` - `:appmock`) | 进度: 80% ✅
- [x] Mock 数据引擎设计与任务规划就位
- [x] 改进计划与技术方案设计 - [详情](business_base/appmock/appmock_technical_design.md)
- [ ] 自动化测试打桩套件扩充

---

### 层级 3：基础服务层 (`docs/service_base/`)

#### 3.1 Vault 加密存储系统 (`vault`) | 进度: 95% ✅
- [x] 统一接口设计与包名规范化 (`com.pancoku.vault`)
- [x] Preferences DataStore (`DataStoreManager`) 与 MMKV + KeyStore 真加密 (AES-256-CFB)
- [x] 应用层入口 `GHStorage`
- [ ] 存储层自动化单元测试补齐 - [详情](service_base/vault/progress.md)

#### 3.2 网络 API 服务 (`network`) | 进度: 70% 🚧
- [x] Retrofit 2 + RxJava 3 + OkHttp 3 基础封装
- [x] `TokenAuthenticator` 全局 Token 过期拦截
- [ ] 全量迁移至 Coroutines / Flow 范式

#### 3.3 应用启动流水线 (`startup`) | 进度: 85% ✅
- [x] 基于 `androidx.startup` 接入模块化异步初始化

#### 3.4 依赖注入服务 (`di`) | 进度: 75% 🚧
- [x] Dagger 2 基础注入 (`AppComponent`, `AppModule`, `GitHub`)
- [ ] 逐步升级向 Hilt / KSP 依赖注入框架演进

#### 3.5 测试与质量基础设施 (`testing`) | 进度: 5% ⏳
- [x] Robolectric + JUnit4 测试基础设施搭建
- [ ] **[同步进行]** 核心业务逻辑与存储层单元测试补齐（目标 80%+ 覆盖率）

---

## 4. 关键里程碑

- [x] **2026-08-22**: 完成 Vault 架构重构与真加密修复
- [x] **2026-08-25**: 完成用户中心 Vault 接入与 401 安全逻辑 (ACCOUNT-001)
- [ ] **(当前任务)**: 推进 Feed 动态流多事件渲染与体验优化 (FEED-001)
