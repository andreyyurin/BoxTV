package ru.sad.boxtv

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import ru.sad.boxtv.ui.di.viewModelModule
import ru.sad.tvframework.di.tvFrameworkModule

class DefaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DefaultApplication)
            loadKoinModules(
                listOf(
                    viewModelModule,
                    tvFrameworkModule
                )
            )
        }
    }
}