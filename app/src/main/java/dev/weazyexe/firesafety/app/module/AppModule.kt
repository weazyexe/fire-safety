package dev.weazyexe.firesafety.app.module

import dagger.Module
import dagger.Provides
import dev.weazyexe.firesafety.app.App

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApp(): App = app
}