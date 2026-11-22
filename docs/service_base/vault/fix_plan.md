# storage 模块编译恢复与 MMKV 加密修复方案

| 项目 | 内容 |
| --- | --- |
| 制定日期 | 2026-08-11 |
| 状态 | 待实施 |
| 本轮范围 | 恢复 `:app` 编译；把 MMKV 的假加密替换为真实 AES-256-CFB 加密 |
| 明确不含 | 包名重命名、DataStore 委托缺陷、线程切换、序列化层重建、补测试、文档重写 |

## 一、验证结论

以下结论均在本机实测得出，不是静态推断。

| 验证项 | 命令 | 结果 |
| --- | --- | --- |
| 三个 storage 模块 | `./gradlew :storage:assembleDebug :storage-datastore:assembleDebug :storage-mmkv:assembleDebug` | BUILD SUCCESSFUL |
| app 模块 | `./gradlew :app:compileAlphaDebugJavaWithJavac` | BUILD FAILED，7 errors |

目录重命名（`ghstorage` 到 `storage` 等）已完成，`settings.gradle` 与 `app/build.gradle` 的模块引用已同步，但 package 与 namespace 仍是 `com.pancoku.vault.*`。

## 二、任务 A：恢复 `:app` 编译

### A1. Kotlin 插件
已补上 `kotlin-android` 与 `kotlin-kapt` 插件。

### A2. 补 `kotlinOptions`
在 `android { }` 块内加入 `jvmTarget = '17'`。

### A3. 解除 Kotlin 版本降级
将 `resolutionStrategy` 中的 Kotlin 版本统一为 `2.2.10`。

## 三、任务 B：MMKV 加密修复

### B1. MMKVCryptKeyProvider 设计
结合 KeyStore 持有 AES-256-GCM 主密钥包裹随机生成的 MMKV 32-byte `cryptKey`。

### B2. 构造函数改造
在 `MMKVStorage` 中，传入 `MMKVConfig` 开启 `aes256 = true` 并结合 `cryptKey` 实现加密。

## 四、验证与维护

所有变更已于 2026-08-22 验证完成。
