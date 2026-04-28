package org.chevalierlabsas.cashier.core.di

import android.app.Application
import org.koin.android.ext.koin.androidContext

class KashierApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KashierApp)
        }
    }
}
