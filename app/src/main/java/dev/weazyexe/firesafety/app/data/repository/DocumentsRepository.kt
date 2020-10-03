package dev.weazyexe.firesafety.app.data.repository

import dev.weazyexe.firesafety.app.data.db.DocumentDao
import dev.weazyexe.firesafety.app.data.db.entity.DocumentDbo
import dev.weazyexe.firesafety.domain.Document
import dev.weazyexe.firesafety.utils.extensions.toCompletable
import dev.weazyexe.firesafety.utils.extensions.transform
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.android.rx.extension.toObservable
import javax.inject.Inject

/**
 * Репозиторий для получения списка документов
 */
class DocumentsRepository @Inject constructor(
    private val documentDao: DocumentDao
) {

    /**
     * Получение документов из БД
     *
     * @param search поисковой параметр
     */
    fun getDocuments(
        search: String,
        limit: Int,
        offset: Int
    ): Observable<List<Document>> =
        RxJavaBridge.toV3Observable(
            documentDao.get(search, limit, offset).map { DataList(it.transform(), limit, offset) }
        )

    /**
     * Создание документа в БД
     *
     * @param document документ, создающийся в БД
     */
    fun createDocument(document: Document): Completable =
        RxJavaBridge.toV3Completable(
            documentDao.insert(DocumentDbo(document.id, document.title, document.link))
        )
}