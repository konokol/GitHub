# AI Agent 指令与项目规约

欢迎，AI 助手。在参与本项目（GitHub Android）的开发时，你必须时刻牢记项目的核心目标：**完成功能完备且测试充分的 GitHub App 客户端。**

## 1. 核心目标
- **业务完成度**: 优先确保 GitHub 的核心功能（仓库、Issue、PR、通知等）按计划实现。
- **质量保证**: 所有功能开发必须配套相应的测试。目标是实现 80% 以上的代码覆盖率。

## 2. 开发规约 (Hard Rules)

1.  **🚫 禁止新建 Java 文件**: 任何新增功能、类或工具都必须使用 Kotlin 编写。
2.  **测试优先**: 每当实现一个新功能或修复一个 Bug，必须检查并补充对应的单元测试或集成测试。
3.  **GHStorage 存储**: 严禁直接使用 `SharedPreferences`。必须使用 `GHStorage` 接口进行持久化。
4.  **异步编程**: 统一使用 Kotlin Coroutines 处理异步任务。
5.  **进度管理**: 修改进度时必须遵循 `docs/progress.md` 的全景管理规则（关注功能进度而非语言迁移细节）。

## 3. 详细规约索引
- [Kotlin & Java 编码规约](conventions/kotlin_java.md)
- [文档与进度维护规约](conventions/documentation.md)
- [存储层 (Vault) 集成指南](../docs/vault/integration_guide.md)

## 4. 如何执行
- 在动手写代码前，先核对 `docs/progress.md` 确认当前功能模块的进度。
- 始终保持对测试覆盖率的敏感，不要提交没有测试的功能代码。
- 遵循“业务功能驱动，技术架构辅助”的原则。
