package com.github

import android.content.Context
import com.github.core.AppComponent
import com.github.core.AppModule
import com.github.core.DaggerAppComponent
import com.github.core.net.NetModule

/**
 * Global entry point for accessing Dagger components and application-wide dependencies.
 */
object GitHub {
    private lateinit var appComponent: AppComponent

    @JvmStatic
    fun init(context: Context): GitHub {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .netModule(NetModule())
            .build()
        return this
    }

    @JvmStatic
    fun appComponent(): AppComponent = appComponent
}
