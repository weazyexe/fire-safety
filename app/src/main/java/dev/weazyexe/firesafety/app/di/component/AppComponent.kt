package dev.weazyexe.firesafety.app.di.component

import dagger.Component
import dev.weazyexe.firesafety.app.di.module.AppModule
import dev.weazyexe.firesafety.app.di.module.NetworkModule
import dev.weazyexe.firesafety.ui.main.MainActivity

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(mainActivityView: MainActivity)
}