package dev.weazyexe.firesafety.app.di.component

import dagger.Component
import dev.weazyexe.firesafety.app.di.module.AppModule
import dev.weazyexe.firesafety.app.di.module.DatabaseModule
import dev.weazyexe.firesafety.app.di.module.NetworkModule
import dev.weazyexe.firesafety.ui.screen.main.MainActivity
import dev.weazyexe.firesafety.ui.screen.main.terms.TermsViewModel

@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(vm: TermsViewModel)
}