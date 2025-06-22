package ru.sad.boxtv.ui.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sad.boxtv.ui.feature.tv.TVViewModel

val viewModelModule = module {
    viewModel { TVViewModel(get()) }
}