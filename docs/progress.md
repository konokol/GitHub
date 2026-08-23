# GitHub App 项目总进度表

## 1. 项目总目标
**完成一个功能完备、测试充分的 GitHub Android 客户端。**
- 覆盖 GitHub 核心业务流程（登录、Feed 流、仓库浏览、Issue/PR 跟踪、通知等）。
- 实现 80% 以上的代码测试覆盖率。
- 确保高性能（基于 Vault 加密存储）和高稳定性。

---

## 2. 核心功能进展

### A. 认证与账户 (Authentication) | 进度: 60% 🚧
- [x] GitHub OAuth 授权流程实现
- [x] 登录、登出、闪屏页 (Splash)
- [ ] 用户中心 (UserCenter) 存储逻辑升级为 Vault (进行中)
- [ ] 登录过期处理与 Token 刷新机制

### B. 动态与 Feed (Dashboard/Activity) | 进度: 40% 🚧
- [x] Feed 列表基础框架 (MVP)
- [x] 支持渲染 Push, Create, Delete 等多种事件类型
- [ ] 分页加载与下拉刷新优化
- [ ] 复杂事件（如 IssueComment, PullRequestReview）的详细渲染

### C. 仓库浏览 (Repository) | 进度: 10% ⏳
- [x] 基础数据模型 (Repository.java)
- [ ] 仓库详情页实现 (Info, Code, Commits, Releases)
- [ ] 我的仓库列表
- [ ] 仓库搜索结果展示

### D. Issue 与 Pull Request | 进度: 5% ⏳
- [x] 基础数据模型 (Issue.java)
- [ ] Issue 列表与详情
- [ ] PR 列表与详情
- [ ] 评论列表展示与发送回复

### E. 用户/组织资料 (Profile) | 进度: 10% ⏳
- [x] 侧边栏 Profile 简要信息展示
- [ ] 个人主页详情 (Overview, Repositories, Starred)
- [ ] 组织主页实现

### F. 通知中心 (Notification) | 进度: 5% ⏳
- [x] 导航入口与占位 Fragment
- [ ] 通知消息列表展示
- [ ] 通知标记为已读/删除操作

### G. 全局搜索 (Search) | 进度: 0% ⏳
- [ ] 搜索主页
- [ ] 仓库、用户、Issue 的分类搜索

### H. 设置与分享 (Settings/Sharing) | 进度: 5% ⏳
- [x] 基础设置 Fragment 框架
- [ ] 暗黑模式切换
- [ ] 应用内分享功能实现

---

## 3. 基础架构进展

### A. 存储系统 (Vault - 漫威主题) | 进度: 90% ✅
- [x] 统一接口设计与包名规范化 (`com.pancoku.vault`)
- [x] 基于 DataStore 的通用存储实现
- [x] 基于 MMKV + AndroidKeyStore 的加密存储实现
- [x] 全局单例 `GHStorage` 应用层接入
- **详情**: [Vault 进度](vault/progress.md)

### B. 通讯与核心 (Core) | 进度: 70% 🚧
- [x] 网络请求库 (Retrofit/OkHttp) 封装
- [x] 应用异步初始化流水线 (AsyncAppInitializer)
- [x] 基础日志系统 (Logan)
- [ ] 架构整体向 Kotlin + MVVM/MVI 演进
- **详情**: [Core 基础设施计划](core/tasks.md)

---

## 4. 测试进展 (Testing) | 进度: 5% ⏳
- [x] 测试基础设施搭建
- [ ] 详情见: [测试任务清单](testing/tasks.md)

---

## 5. 关键里程碑
- [x] 2026-08-07: 完成 Vault 存储方案设计
- [x] 2026-08-22: 完成 Vault 架构重构与真加密修复
- [ ] (Next): 用户中心与登录凭证安全接入 Vault (详见 [Auth 计划](auth/tasks.md))
- [ ] (Pending): 启动仓库详情页功能开发 (详见 [Repo 计划](repo/tasks.md))
