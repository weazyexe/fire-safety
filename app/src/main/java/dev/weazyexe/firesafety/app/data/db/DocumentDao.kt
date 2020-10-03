package dev.weazyexe.firesafety.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.weazyexe.firesafety.app.data.db.entity.DocumentDbo
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface DocumentDao {

    @Query("SELECT * FROM documents WHERE title LIKE :query ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun get(
        query: String,
        limit: Int,
        offset: Int
    ): Observable<List<DocumentDbo>>

    @Query("SELECT count(*) FROM documents")
    fun getSize(): Observable<Int>

    @Query("SELECT * FROM documents WHERE id = :id")
    fun getById(id: Int): DocumentDbo?

    @Insert
    fun insert(document: DocumentDbo)

    @Query("DELETE FROM documents")
    fun deleteTable(): Completable
}