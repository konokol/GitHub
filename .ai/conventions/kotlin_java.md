# Kotlin & Java 编码规约 (AI 专供)

## 核心语言规则

### 1. 禁止新建 Java 文件 (HARD RULE)
- **动作**: 永远不要创建以 `.java` 结尾的新文件。
- **替代**: 始终使用 `.kt` 文件。
- **例外**: 除非用户明确要求（且有极其特殊的理由），否则一律拒绝新建 Java 文件。

### 2. 禁止主动迁移存量 Java 代码 (NEW HARD RULE)
- **动作**: 除非该 Java 文件存在无法通过互操作解决的严重 Bug，或用户明确指令迁移，否则**不得**主动将其转换为 Kotlin。
- **目的**: 集中精力于 `docs/progress.md` 中定义的业务功能补齐。

## Kotlin 规范

### 1. 命名与风格
- 类名：`PascalCase`
- 函数与变量：`camelCase`
- 常量：`SCREAMING_SNAKE_CASE`
- 遵循 [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)。

### 2. 空安全 (Null Safety)
- 严禁滥用 `!!` 非空断言。
- 优先使用 `?` 可空类型配合 `?.let {}` 或 `?:`（Elvis 操作符）。
- 在构造函数或注入点使用 `lateinit var` 仅限 Dagger 注入。

### 3. 协程 (Coroutines)
- 使用 `suspend` 函数标记异步方法。
- 避免在全局或硬编码的 Dispatchers 上运行，优先使用注入的或通用的 `Dispatchers.IO` / `Dispatchers.Main`。
- 在 ViewModel 或特定生命周期作用域内运行协程。

## Java 兼容性 (Legacy)

- **维护模式**: 现有的 Java 代码应保持最小化维护。
- **互操作性**: 在 Kotlin 编写新代码时，确保通过 `@JvmStatic`、`@JvmOverloads` 等注解保持对现有 Java 调用方的兼容性（如果需要）。
- **Dagger**: 由于项目目前使用 `kapt`，请注意 Kotlin 中的 Dagger 注解（如 `@Inject constructor`）与 Java 的差异。

## 代码审查关注点
- 是否有 `.java` 文件新增？（必须拦截）
- 是否使用了 `SharedPreferences` 而非 `GHStorage`？
- 是否漏掉了 `suspend` 关键字？
