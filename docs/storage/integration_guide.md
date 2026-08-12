# GHStorage 在 App Module 中的集成指南

## 集成概述

成功将 GHStorage 集成到 GitHub app module 中，包括依赖配置、工具类创建和 Dagger 依赖注入配置。

## 集成步骤

### 1. 添加依赖配置

在 `app/build.gradle` 中添加了以下依赖：

```gradle
dependencies {
    // 现有依赖...
    
    // GHStorage 模块
    implementation project(":ghstorage-datastore")
    implementation project(":ghstorage-mmkv")
}
```

### 2. 初始化 MMKV

在 `GitHubApplication.java` 中添加 MMKV 初始化：

```java
@Override
public void onCreate() {
    super.onCreate();
    // 初始化 MMKV（用于支持加密存储）
    MMKVFactory.initialize(this);
    
    AsyncAppInitializer.getInstance(this).asyncInit();
}
```

### 3. 创建工具类

创建了 `GHStorageHelper` 工具类，提供以下功能：

#### 主要方法

- `getStorage(String name)` - 获取指定名称的存储实例
- `getDefaultStorage()` - 获取默认存储实例
- `getUserSettingsStorage()` - 获取用户设置存储实例
- `getCacheStorage()` - 获取缓存存储实例
- `getEncryptedStorage(String name, EncryptionType encryption)` - 获取加密存储实例
- `getSecureStorage()` - 获取安全存储实例（默认加密）

### 4. 配置 Dagger 依赖注入

在 `AppModule.java` 中添加了 GHStorageHelper 的提供方法：

```java
@Provides
@Singleton
com.github.utils.GHStorageHelper provideGHStorageHelper(Context context) {
    return new com.github.utils.GHStorageHelper(context);
}
```

在 `AppComponent.java` 中添加了 GHStorageHelper 接口：

```java
com.github.utils.GHStorageHelper ghStorageHelper();
```

## 使用示例

### 在 Activity 中使用

```java
public class MainActivity extends Activity {
    @Inject
    GHStorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getApplication())
            .getAppComponent()
            .inject(this);
        
        // 使用存储
        useStorage();
    }
    
    private void useStorage() {
        new Thread(() -> {
            KVStorage storage = storageHelper.getDefaultStorage();
            
            // 存储数据
            storage.put("username", "john_doe");
            
            // 读取数据
            Result<String> result = storage.get("username", String.class);
            if (result.isSuccess()) {
                String username = result.getOrNull();
                // 处理数据
            }
        }).start();
    }
}
```

### 加密存储示例

```java
// 使用加密存储
KVStorage secureStorage = storageHelper.getSecureStorage();

// 存储敏感数据
secureStorage.put("token", "secret_token");
secureStorage.put("password", "user_password");

// 读取敏感数据
Result<String> tokenResult = secureStorage.get("token", String.class);
if (tokenResult.isSuccess()) {
    String token = tokenResult.getOrNull();
}
```

### 用户设置存储

```java
// 使用用户设置存储
KVStorage userSettings = storageHelper.getUserSettingsStorage();

// 存储设置
userSettings.put("theme", "dark");
userSettings.put("language", "zh_CN");
userSettings.put("notifications_enabled", true);

// 读取设置
Result<String> themeResult = userSettings.get("theme", String.class);
```

## 存储类型选择

### DataStore Storage (默认)
- **使用场景**: 通用数据存储、用户设置、缓存数据
- **优势**: 基于协程、事务保证、错误处理完善
- **性能**: 适合中小数据量

### MMKV Storage (加密)
- **使用场景**: 敏感数据、需要高性能的场景
- **优势**: 高性能、支持加密、跨平台
- **性能**: 适合大数据量和高频访问

## 注意事项

1. **异步操作**: 所有 KVStorage 操作都是异步的，需要在协程或线程中执行
2. **线程安全**: KVStorage 实例是线程安全的，可以在不同线程中使用
3. **内存缓存**: GHStorageHelper 提供实例缓存，避免重复创建
4. **错误处理**: 使用 Result<T> 模式处理错误，建议检查 isSuccess()

## 集成验证

完成集成后，可以通过以下方式验证：

1. **编译检查**: 项目能够成功编译
2. **依赖注入**: 检查 Dagger 注入是否正常
3. **基本操作**: 尝试基本的存取操作
4. **加密功能**: 验证加密存储功能

## 后续优化

1. **性能监控**: 添加存储操作的性能监控
2. **数据迁移**: 支持从 SharedPreferences 迁移数据
3. **扩展功能**: 添加更多实用方法
4. **错误处理**: 完善错误处理和日志记录

## 文件清单

### 修改的文件
- `app/build.gradle` - 添加 GHStorage 依赖
- `app/src/main/java/com/github/GitHubApplication.java` - 初始化 MMKV
- `app/src/main/java/com/github/core/AppModule.java` - 添加 Dagger 依赖注入
- `app/src/main/java/com/github/core/AppComponent.java` - 添加 Dagger 接口

### 新建的文件
- `app/src/main/java/com/github/utils/GHStorageHelper.java` - GHStorage 工具类

## 集成状态

✅ 依赖配置完成
✅ MMKV 初始化完成  
✅ 工具类创建完成
✅ Dagger 依赖注入配置完成
✅ 基本功能验证通过

集成已成功完成，可以在项目中使用 GHStorage 进行数据存储操作。