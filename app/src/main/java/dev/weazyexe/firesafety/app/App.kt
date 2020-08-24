package dev.weazyexe.firesafety.app

import android.app.Application
import dev.weazyexe.firesafety.app.component.AppComponent
import dev.weazyexe.firesafety.app.component.DaggerAppComponent
import dev.weazyexe.firesafety.app.module.AppModule

class App: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}