# AI Agent 指令与项目规约

欢迎，AI 助手。在参与本项目（GitHub Android）的开发时，你必须严格遵守以下规约。

## 核心原则

1.  **🚫 禁止新建 Java 文件**：本项目已全面转向 Kotlin 优先策略。任何新增功能、类或工具都必须使用 Kotlin 编写。
2.  **Kotlin 优先**：优先将旧有的 Java 代码重构为 Kotlin，尤其是核心业务逻辑。
3.  **GHStorage 存储**：禁止直接使用原生 `SharedPreferences`。必须使用 `GHStorage` 提供的统一接口，并根据数据敏感度选择 `getSecureStorage()` (MMKV) 或 `getDefaultStorage()` (DataStore)。
4.  **异步协程**：所有异步操作应基于 Kotlin Coroutines 实现，避免使用原始线程。

## 详细规约索引

有关特定语言和架构的详细规则，请参阅以下文档：

-   [Kotlin & Java 编码规约](conventions/kotlin_java.md)
-   [存储层 (GHStorage) 集成指南](../docs/storage/integration_guide.md)
-   [项目总体编码规约 (docs)](../docs/coding_standards.md)

## 如何执行

-   在修改文件前，先检查该文件的语言。如果是 Java 文件且涉及大改，请先考虑将其转换为 Kotlin。
-   如果你被要求创建一个新类，**必须**创建 `.kt` 文件。
-   始终保持对 Dagger (kapt/ksp) 和 DataBinding 兼容性的感知。
