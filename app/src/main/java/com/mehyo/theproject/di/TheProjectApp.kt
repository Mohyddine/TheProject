package com.mehyo.theproject.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TheProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@TheProjectApp)
            // use modules
            modules(listOf(networkModule, repositoryModule, useCaseModule, viewModelModule))
        }

    }
}