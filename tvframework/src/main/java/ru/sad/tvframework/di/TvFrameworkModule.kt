package ru.sad.tvframework.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sad.tvframework.api.TifManager
import ru.sad.tvframework.impl.TifManagerImpl

val tvFrameworkModule = module {
    single<TifManager> { TifManagerImpl(androidContext()) }
}