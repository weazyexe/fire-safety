package dev.weazyexe.firesafety.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.weazyexe.firesafety.app.base.Transformable
import dev.weazyexe.firesafety.domain.Document

@Entity(tableName = "documents")
data class DocumentDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "link")
    val link: String

) : Transformable<Document> {

    override fun transform(): Document = Document(id, title, link)

}