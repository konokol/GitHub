# GitHub 项目编码规约

## 概述

本文档定义了 GitHub 项目的编码标准和最佳实践，旨在提高代码质量、一致性和可维护性。

## 语言选择原则

### 🚫 **核心规则：禁止新建 Java 文件**

> **重要声明**：本项目禁止新建 Java 文件，所有新增代码必须使用 Kotlin 编写。

#### 规则详情
- ✅ **必须使用 Kotlin**: 所有新文件、新类、新功能都必须使用 Kotlin 编写
- ❌ **禁止新建 Java**: 禁止创建任何新的 `.java` 文件
- 🔄 **现有 Java 文件**: 可以维护和修改现有的 Java 文件，但不能创建新的 Java 文件

#### 例外情况
- 只有在以下特殊情况下，经技术负责人批准后才能创建 Java 文件：
  - 需要与第三方 Java-only 库进行深度集成
  - 性能优化的特定场景
  - 遗留系统的兼容性要求

## Kotlin 优先策略

### 新项目/新模块
- 所有新项目、新模块 100% 使用 Kotlin
- 使用 Kotlin 编程范式和最佳实践
- 充分利用 Kotlin 语言特性

### 现有项目改造
- 优先将 Java 代码迁移到 Kotlin
- 重构现有代码时考虑使用 Kotlin
- 逐步减少 Java 代码比例

## 编码规范

### 代码风格
- 遵循 [Android Kotlin 编码规范](https://developer.android.com/kotlin/style-guide)
- 使用官方推荐的 Kotlin 代码风格
- 统一使用 Kotlin 的命名约定

### 文件组织
- Kotlin 文件使用 `.kt` 扩展名
- 一个文件可以包含多个类（通常一个公开类 + 相关私有类）
- 使用 package 声明定义文件归属

## 代码质量

### 代码审查检查清单
- [ ] 新代码是否使用 Kotlin 编写
- [ ] 是否使用了 Kotlin 的高级特性（扩展函数、数据类等）
- [ ] 是否遵循 Kotlin 编码规范
- [ ] 是否正确处理空安全
- [ ] 是否使用了 Kotlin 协程进行异步操作

### 性能考虑
- 优先使用 Kotlin 协程替代线程
- 使用 Kotlin 的标准库函数简化代码
- 充分利用 Kotlin 的内联函数优化性能

## 迁移策略

### Java 到 Kotlin 迁移优先级
1. **核心业务逻辑** - 优先迁移重要的业务代码
2. **工具类和辅助类** - 次要迁移
3. **测试代码** - 最后迁移测试类

### 迁移工具
- 使用 Android Studio 的 "Convert Java File to Kotlin File" 功能
- 逐步迁移，避免大规模重构
- 每次迁移后进行充分测试

## 项目结构

### Kotlin 项目结构
```
app/src/main/kotlin/com/github/       # 应用代码
library/src/main/kotlin/com/github/  # 库代码
ghstorage/src/main/kotlin/...        # 存储模块
```

### 包命名约定
- 使用公司域名反向：`com.pancoku.*`
- 按功能模块组织包结构
- 保持包结构扁平化

## 依赖管理

### Kotlin 库优先
- 优先选择 Kotlin 原生的库
- 避免使用纯 Java 库
- 选择支持协程的异步库

### 版本管理
- 使用 Kotlin 版本的依赖库
- 保持 Kotlin 编译器和库的版本兼容
- 定期更新到最新的稳定版本

## 异步编程

### 协程优先
- 所有异步操作使用 Kotlin 协程
- 使用 `suspend` 函数标记异步操作
- 使用 `Flow` 进行数据流处理

### 错误处理
- 使用 Kotlin 的 Result<T> 进行错误处理
- 避免使用传统的 try-catch 异常处理
- 提供清晰的错误信息

## 测试规范

### 测试语言
- 所有测试代码使用 Kotlin 编写
- 使用 Kotlin 测试框架和断言库
- 充分利用 Kotlin 的测试特性

### 测试覆盖率
- 新功能必须有单元测试
- 测试覆盖率应达到 80% 以上
- 使用 Kotlin 的测试 DSL 简化测试代码

## 文档要求

### KDoc 格式
- 所有公共 API 必须有 KDoc 注释
- 使用标准的 KDoc 格式
- 包含示例代码和使用说明

### 代码注释
- 优先使用自解释的代码
- 复杂逻辑必须有注释说明
- 更新注释与代码同步

## 工具配置

### 构建配置
```kotlin
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += [
            "-opt-in=kotlin.RequiresOptIn"
        ]
    }
}
```

### 静态分析
- 启用 Kotlin 静态分析工具
- 配置 ktlint 进行代码风格检查
- 使用 Detekt 进行代码质量分析

## 团队协作

### 代码审查
- 所有 Kotlin 代码必须经过 Code Review
- 检查是否遵循编码规范
- 确认没有引入新的 Java 文件

### 知识分享
- 定期组织 Kotlin 最佳实践分享
- 建立内部 Kotlin 知识库
- 鼓励团队学习和使用 Kotlin 高级特性

## 违规处理

### 规则违反
- 检查代码审查过程，禁止新建 Java 文件
- 发现违规代码立即要求重写为 Kotlin
- 记录违规情况，进行团队培训

### 持续改进
- 定期检查项目中 Java 文件的数量
- 制定减少 Java 文件的计划
- 监控 Kotlin 代码比例的增长

## 参考资料

- [Kotlin 官方文档](https://kotlinlang.org/docs/)
- [Android Kotlin 指南](https://developer.android.com/kotlin)
- [Kotlin 编码规范](https://kotlinlang.org/docs/coding-conventions.html)

---

**版本**: 1.0  
**最后更新**: 2026-08-07  
**维护者**: GitHub 开发团队

> **注意**: 本规约会根据项目发展和技术演进进行更新，团队成员应定期关注最新版本。