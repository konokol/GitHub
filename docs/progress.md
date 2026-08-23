# GitHub App 项目总进度表

## 1. 项目总目标
**补齐核心基础功能，完成一个功能完备、测试充分的 GitHub Android 客户端。**
- **当前重点**: 优先实现未完成的业务模块（仓库详情、Issue/PR、通知等），确保业务闭环。
- **质量保证**: 实现 80% 以上的代码测试覆盖率。
- **技术支撑**: 基于 Vault 加密存储和稳定的核心架构。

---

## 2. 核心功能进展

### A. 认证与账户 (Authentication) | 进度: 70% 🚧
- [x] GitHub OAuth 授权流程实现
- [x] 登录、登出、闪屏页 (Splash)
- [x] 用户中心 (UserCenter) 存储逻辑升级为 Vault (已交付 Review)
- [ ] 关键: 完善登录过期处理与 Token 刷新机制
- **详情**: [Auth 计划与简报](auth/tasks.md)

### B. 动态与 Feed (Dashboard/Activity) | 进度: 40% 🚧
- [x] Feed 列表基础框架 (MVP)
- [x] 支持渲染 Push, Create, Delete 等多种事件类型
- [ ] **[重点]** 分页加载与下拉刷新优化
- [ ] **[增强]** 复杂事件（如 IssueComment, PullRequestReview）的详细渲染

### C. 仓库浏览 (Repository) | 进度: 10% ⏳
- [x] 基础数据模型 (Repository.java)
- [ ] **[急需]** 仓库详情页实现 (Info, Code, Commits, Releases)
- [ ] 我的仓库列表与搜索结果展示
- **详情**: [Repo 计划](repo/tasks.md)

### D. Issue 与 Pull Request | 进度: 5% ⏳
- [x] 基础数据模型 (Issue.java)
- [ ] **[急需]** Issue/PR 列表与详情页面
- [ ] 评论列表展示与发送回复功能

### E. 通知中心 (Notification) | 进度: 5% ⏳
- [x] 导航入口与占位 Fragment
- [ ] **[重点]** 通知消息列表展示与已读/删除操作

### F. 用户/组织资料 (Profile) | 进度: 10% ⏳
- [x] 侧边栏 Profile 简要信息展示
- [ ] 个人主页与组织主页详细信息展示

---

## 3. 基础架构进展

### A. 存储系统 (Vault) | 进度: 100% ✅
- [x] 统一接口设计与包名规范化 (`com.pancoku.vault`)
- [x] DataStore 与 MMKV + KeyStore 加密实现
- [x] 稳定性修复 (Manager 与 线程安全容器)
- **详情**: [Vault 进度](vault/progress.md)

### B. 通讯与核心 (Core) | 进度: 70% 🚧
- [x] 网络请求库 (Retrofit/OkHttp) 封装
- [x] 应用异步初始化流水线
- [ ] 架构向 Kotlin + MVVM 逐步演进 (作为功能开发的副产品)

---

## 4. 测试进展 (Testing) | 进度: 5% ⏳
- [x] 测试基础设施搭建
- [ ] **[同步进行]** 核心业务逻辑与存储层单元测试

---

## 5. 关键里程碑
- [x] 2026-08-22: 完成 Vault 架构重构与真加密修复
- [ ] **(当前任务)**: 补齐用户中心与登录流的 Vault 接入及安全逻辑
- [ ] **(下一步)**: 启动仓库详情页与 Issue/PR 核心功能开发
