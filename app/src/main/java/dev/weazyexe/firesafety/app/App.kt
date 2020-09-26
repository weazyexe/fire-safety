package dev.weazyexe.firesafety.app

import android.app.Application
import dev.weazyexe.firesafety.app.di.component.AppComponent
import dev.weazyexe.firesafety.app.di.component.DaggerAppComponent
import dev.weazyexe.firesafety.app.di.module.AppModule
import dev.weazyexe.firesafety.app.di.module.NetworkModule

class App: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }
}