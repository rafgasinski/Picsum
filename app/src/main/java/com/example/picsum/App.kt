package com.example.picsum

import android.app.Application
import com.example.picsum.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val list = listOf(
            getRemoteModule(BuildConfig.BASE_URL),
            getDomainModule(),
            getPresentationModule(),
            getUtilsModule()
        )

        startKoin {
            modules(list)
            androidContext(this@App)
        }
    }
}