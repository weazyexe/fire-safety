package dev.weazyexe.firesafety.app.data.db

import androidx.room.*
import dev.weazyexe.firesafety.app.data.db.entity.DocumentDbo
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface DocumentDao {

    @Query("SELECT * FROM documents WHERE :query LIKE title ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun get(
        query: String,
        limit: Int = DEFAULT_LIMIT,
        offset: Int = DEFAULT_OFFSET
    ): Observable<List<DocumentDbo>>

    @Query("SELECT * FROM documents WHERE id = :id")
    fun getById(id: Int): DocumentDbo?

    @Insert
    fun insert(document: DocumentDbo): Completable

    @Query("DELETE FROM documents")
    fun deleteTable() : Completable
}