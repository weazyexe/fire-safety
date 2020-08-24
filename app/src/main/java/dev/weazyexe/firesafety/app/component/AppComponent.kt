package dev.weazyexe.firesafety.app.component

import dagger.Component
import dev.weazyexe.firesafety.app.module.AppModule
import dev.weazyexe.firesafety.ui.main.MainActivity

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivityView: MainActivity)
}