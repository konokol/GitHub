package com.pankoku.appmock

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProxyInterceptorTest {

    private val appMock = AppMock.instance
    private val userId = "test_user"
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        appMock.setEnabled(userId, false)
    }

    @After
    fun tearDown() {
        appMock.setEnabled(userId, false)
        server.shutdown()
    }

    @Test
    fun `disabled - request url not modified`() {
        appMock.setEnabled(userId, false)

        val client = OkHttpClient.Builder()
            .addInterceptor(ProxyInterceptor(appMock, userId))
            .build()

        server.enqueue(MockResponse().setBody("ok"))

        val request = Request.Builder()
            .url(server.url("/test"))
            .build()

        val response = client.newCall(request).execute()
        assertEquals("ok", response.body?.string())
    }

    @Test
    fun `enabled - request url redirected to proxy`() {
        val originalServer = MockWebServer()
        originalServer.start()
        originalServer.enqueue(MockResponse().setBody("original"))

        appMock.setProxy(userId, server.hostName, server.port)
        appMock.setEnabled(userId, true)

        val client = OkHttpClient.Builder()
            .addInterceptor(ProxyInterceptor(appMock, userId))
            .build()

        server.enqueue(MockResponse().setBody("proxied"))

        val request = Request.Builder()
            .url(originalServer.url("/users/test"))
            .build()

        val response = client.newCall(request).execute()
        assertEquals("proxied", response.body?.string())

        val recorded = server.takeRequest()
        assertEquals("/users/test", recorded.path)

        originalServer.shutdown()
    }

    @Test
    fun `enabled - query parameters preserved`() {
        val originalServer = MockWebServer()
        originalServer.start()

        appMock.setProxy(userId, server.hostName, server.port)
        appMock.setEnabled(userId, true)

        val client = OkHttpClient.Builder()
            .addInterceptor(ProxyInterceptor(appMock, userId))
            .build()

        server.enqueue(MockResponse().setBody("ok"))

        val request = Request.Builder()
            .url(originalServer.url("/search?q=hello&page=1"))
            .build()

        client.newCall(request).execute()

        val recorded = server.takeRequest()
        assertEquals("/search?q=hello&page=1", recorded.path)

        originalServer.shutdown()
    }

    @Test
    fun `different users have independent configs`() {
        val user1 = "user_1"
        val user2 = "user_2"

        appMock.setProxy(user1, server.hostName, server.port)
        appMock.setEnabled(user1, true)
        appMock.setEnabled(user2, false)

        // user1 启用代理
        assertTrue(appMock.config(user1).isEnabled)
        assertEquals(server.hostName, appMock.config(user1).proxyHost)

        // user2 未启用代理
        assertFalse(appMock.config(user2).isEnabled)
        assertEquals("", appMock.config(user2).proxyHost)

        // 清理
        appMock.setEnabled(user1, false)
        appMock.setEnabled(user2, false)
    }
}
