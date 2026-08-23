package com.github

import android.content.Context
import com.github.core.AppComponent
import com.github.core.AppModule
import com.github.core.DaggerAppComponent
import com.github.core.net.NetConfig
import com.github.core.net.NetModule

/**
 * a Global single Class
 */
class GitHub private constructor(application: Context) {

    init {
        sDaggerAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .netModule(NetModule(NetConfig.defaultConfig()))
            .build()
    }

    companion object {
        private var mGitHub: GitHub? = null
        private lateinit var sDaggerAppComponent: AppComponent

        @JvmStatic
        fun init(application: Context): GitHub {
            if (mGitHub == null) {
                synchronized(GitHub::class.java) {
                    if (mGitHub == null) {
                        mGitHub = GitHub(application)
                    }
                }
            }
            return mGitHub!!
        }

        @JvmStatic
        fun appComponent(): AppComponent = sDaggerAppComponent
    }
}
