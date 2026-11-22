# GHStorage (Vault) 集成指南

## 集成概述

成功将 GHStorage (Vault) 集成到 GitHub app 模块中，包括依赖配置、工具类创建和 Dagger 依赖注入配置。

## 集成步骤

### 1. 依赖配置 (`app/build.gradle`)
```groovy
dependencies {
    implementation project(":vault")
    implementation project(":vault-datastore")
    implementation project(":vault-mmkv")
}
```

### 2. 初始化 MMKV
在 `GitHubApplication.java` 中进行 MMKV 初始化：
```java
MMKVFactory.initialize(this);
```

### 3. 应用层统一入口 (`GHStorage.kt`)
```kotlin
@Singleton
class GHStorage @Inject constructor(
    private val context: Context
) {
    fun getSecureStorage(): KVStorage = getEncryptedStorage("secure", EncryptionType.AES_256_CFB)
}
```
