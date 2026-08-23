# 仓库浏览任务 (Repository)

## 当前状态: 🚧 详情页框架已建立 (等待 Review)

## 任务进展
1. **[紧急] 仓库详情页框架**:
    - [x] 创建 `RepositoryActivity` (Kotlin) 作为详情页宿主。
    - [x] 设计 `activity_repository.xml` (TabLayout + ViewPager2)。
    - [x] 实现 `RepoPagerAdapter` 承载不同 Tab。
    - [x] 接入 `RepositoryService` 与 `RepoDetailViewModel`。
2. **[关键] 详情展示**:
    - [ ] **Tab 1: Info**: 展示 README 或基本 Meta 信息。
    - [ ] **Tab 2: Code**: 树状目录浏览。
    - [ ] **Tab 3: Commits**: 提交历史。
    - [ ] **Tab 4: Releases**: 发布版本。
3. **[支撑] 数据加载**:
    - [x] 定义 `RepositoryService` 接口。
    - [x] 实现 `RepoDetailViewModel` 处理业务逻辑。

## 交付产物
- [任务完成简报](review_reports/summary.md)
- `app/src/main/kotlin/com/github/app/repo/RepositoryActivity.kt`
- `app/src/main/kotlin/com/github/api/RepositoryService.kt`
- `app/src/main/kotlin/com/github/app/repo/RepoDetailViewModel.kt`

## 关联业务
- 导航: 可通过 `RepositoryActivity.start(context, owner, repo)` 跳转。
