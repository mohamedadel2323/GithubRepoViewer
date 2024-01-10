package com.example.githubrepoviewer.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: Dispatchers)

enum class Dispatchers{
    Default,
    IO,
    Main,
    Unconfined
}