package com.pankoku.appmock

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * OkHttp 拦截器，根据用户标识将网络请求转发到代理服务器
 */
class ProxyInterceptor(
    private val appMock: AppMock,
    private val userId: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val config = appMock.config(userId)

        if (!config.isEnabled) {
            return chain.proceed(original)
        }

        val newUrl: HttpUrl = original.url.newBuilder()
            .host(config.proxyHost)
            .port(config.proxyPort)
            .build()

        val proxyRequest: Request = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(proxyRequest)
    }
}
