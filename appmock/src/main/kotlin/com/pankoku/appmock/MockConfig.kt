package com.pankoku.appmock

/**
 * 单个用户的 Mock 配置
 */
data class MockConfig(
    var proxyHost: String = "",
    var proxyPort: Int = 0,
    var isEnabled: Boolean = false
)
