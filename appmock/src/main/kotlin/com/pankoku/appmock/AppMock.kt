package com.pankoku.appmock

/**
 * AppMock 门面类，支持按用户标识区分配置
 */
class AppMock private constructor() {

    private val configs = mutableMapOf<String, MockConfig>()

    /**
     * 获取指定用户的配置，不存在则自动创建
     */
    fun config(userId: String): MockConfig {
        return configs.getOrPut(userId) { MockConfig() }
    }

    /**
     * 设置指定用户的代理服务器地址
     */
    fun setProxy(userId: String, host: String, port: Int): AppMock {
        config(userId).apply {
            this.proxyHost = host
            this.proxyPort = port
        }
        return this
    }

    /**
     * 设置指定用户是否启用代理拦截
     */
    fun setEnabled(userId: String, enabled: Boolean): AppMock {
        config(userId).isEnabled = enabled
        return this
    }

    /**
     * 创建代理拦截器实例，绑定到指定用户
     */
    fun newInterceptor(userId: String): ProxyInterceptor {
        return ProxyInterceptor(this, userId)
    }

    companion object {
        @JvmField
        val instance: AppMock = AppMock()
    }
}
