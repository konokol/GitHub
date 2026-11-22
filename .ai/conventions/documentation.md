# 文档与进度维护规约

## 1. 核心进度与总纲文件 (`docs/` 根目录)
- **`docs/overview.md`**: 项目总纲与三层架构图，描述整个系统的层级架构（业务层、基础业务层、基础服务层）、模块划分与责任边界。
- **`docs/progress.md`**: 项目总进度表，按三层架构记录每个模块的高层级进度与里程碑。
- **`docs/coding_standards.md`**: 代码规范与编码标准。
- **`docs/agent_knowledge_base.md`**: AI Agent 知识库。

## 2. docs 目录结构规约 (三层树形架构)
`docs/` 目录必须严格遵循 **业务层 -> 基础业务层 -> 基础服务层** 的三层架构目录结构，每一个原子模块对应一个独立的文件夹：

```
docs/
├── overview.md                  # [总纲] 项目总纲与三层架构图
├── progress.md                  # [当前进展] 项目总进度
├── coding_standards.md          # 代码规范与指南
├── agent_knowledge_base.md      # Agent 知识库
├── business/                    # 【1. 业务层 (Business / Feature Layer)】
│   ├── repository/              # 原子模块：仓库浏览与源码 (REPOSITORY-001 当前重点)
│   ├── feed/                    # 原子模块：动态与 Activity Feed
│   ├── issue_pr/                # 原子模块：Issue 与 PR 管理
│   ├── notification/            # 原子模块：通知中心
│   └── profile/                 # 原子模块：个人与组织主页
├── business_base/               # 【2. 基础业务层 (Foundational Business Layer)】
│   ├── account/                 # 原子模块：认证与用户中心 (ACCOUNT-001)
│   ├── web/                     # 原子模块：Web 通用容器与页面 (`BifrostWebView` / `WebActivity`)
│   ├── design_library/          # 原子模块：通用 UI 组件库 (:library)
│   └── appmock/                 # 原子模块：Mock 测试与代理服务 (:appmock)
└── service_base/                # 【3. 基础服务层 (Base Service Layer)】
    ├── vault/                   # 原子模块：Vault 加密存储系统 (:vault, :vault-datastore, :vault-mmkv)
    ├── network/                 # 原子模块：网络请求与 API 服务 (Retrofit / OkHttp)
    ├── startup/                 # 原子模块：应用启动流水线 (AppStartup)
    ├── di/                      # 原子模块：依赖注入服务 (Dagger 2)
    └── testing/                 # 原子模块：测试与质量基础设施 (Unit & Integration Testing)
```

## 3. 原子模块文档组织 (`docs/<layer>/<module>/...`)
每个原子模块文件夹内部可包含：
- `plans/`: 模块设计方案与规划
- `tasks/`: 模块任务清单与 TODO
- `reports/`: 模块交付与 Review 报告 (Walkthrough / Review)
- `README.md` / `progress.md`: 模块说明与细化进度

## 4. 任务交付与 Review 流程 (HARD RULE)
本项目采用 **“双模型协作”** 模式：
- **执行模型 (Current Agent)**:
  - 负责代码编写、重构与自测。
  - 任务完成后产出“任务完成简报” (Summary)，保存在对应原子模块的 `reports/` 目录下。
- **Review 模型 (Another Model)**:
  - 生成“Review 报告”，存储在 `docs/<layer>/<module>/reports/` 目录下。

## 5. 交互与修改规则 (HARD RULE)
- **💬 沟通确认优先**：在与用户讨论方案、架构或逻辑时，**严禁擅自修改代码或文档**。必须先沟通确认，待用户同意方案后再动手修改落盘。
- 执行模型在执行任务后，必须同步更新 `docs/progress.md` 以及对应原子模块中的进度。
- 所有新增文档必须在对应的架构分层与原子模块文件夹中登记，不得放置于非规范目录下。
