package dev.weazyexe.firesafety.app.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.weazyexe.firesafety.app.App
import dev.weazyexe.firesafety.app.data.db.AppDatabase
import dev.weazyexe.firesafety.app.data.db.DocumentDao

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(app: App): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideDocumentsDao(database: AppDatabase): DocumentDao =
        database.documentsDao()
}