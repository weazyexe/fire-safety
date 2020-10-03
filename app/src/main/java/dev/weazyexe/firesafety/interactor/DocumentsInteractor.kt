package dev.weazyexe.firesafety.interactor

import dev.weazyexe.firesafety.app.data.repository.DocumentsRepository
import dev.weazyexe.firesafety.domain.Document
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import io.reactivex.rxjava3.core.Observable
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import javax.inject.Inject

/**
 * Интерактор документов
 */
class DocumentsInteractor @Inject constructor(
    private val documentsRepository: DocumentsRepository
) {

    /**
     * Проверка является ли БД с документами пустой
     */
    fun isDatabaseEmpty(): Observable<Boolean> =
        documentsRepository.getDocumentsSize().map { it == 0 }

    /**
     * Получение документов из [DocumentsRepository]
     *
     * @param search поисковой параметр
     */
    fun getDocuments(
        search: String = "",
        limit: Int = DEFAULT_LIMIT,
        offset: Int = DEFAULT_OFFSET
    ): Observable<DataList<Document>> =
        documentsRepository.getDocuments(search, limit, offset)

    /**
     * Создание документа в БД
     *
     * @param document документ, создающийся в БД
     */
    fun createDocument(document: Document) =
        documentsRepository.createDocument(document)
}