package dev.weazyexe.firesafety.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.weazyexe.firesafety.app.data.db.entity.DocumentDbo

@Database(entities = [DocumentDbo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun documentsDao() : DocumentDao

}