package dev.weazyexe.firesafety.ui.screen.main.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.weazyexe.firesafety.app.App
import dev.weazyexe.firesafety.domain.Document
import dev.weazyexe.firesafety.interactor.DocumentsInteractor
import dev.weazyexe.firesafety.interactor.ParserInteractor
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import dev.weazyexe.firesafety.utils.extensions.default
import dev.weazyexe.firesafety.utils.extensions.getState
import dev.weazyexe.firesafety.utils.extensions.mergeIfNeed
import dev.weazyexe.firesafety.utils.extensions.subscribe
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import java.io.InputStream
import javax.inject.Inject

/**
 * [ViewModel] с логикой получения и записи документов
 */
class LibraryViewModel : ViewModel() {

    @Inject
    lateinit var parserInteractor: ParserInteractor

    @Inject
    lateinit var documentsInteractor: DocumentsInteractor

    val paginationState = MutableLiveData<PaginationState>().default(PaginationState.READY)

    val documents = MutableLiveData<DataList<Document>>().default(DataList.empty())

    private lateinit var libraryInputStream: InputStream

    init {
        App.component.inject(this)
    }

    /**
     * Заполнение базы данных документов данными из csv файла
     *
     * @param inputStream [InputStream] csv файла из ассетов
     */
    fun fillDatabaseIfNeedAndGetData(inputStream: InputStream) {
        libraryInputStream = inputStream
        loadData()
    }

    /**
     * Загрузка следующей порции данных
     */
    fun loadMore(search: String) = loadData(
        search = search,
        offset = documents.value?.nextOffset ?: DEFAULT_OFFSET
    )

    /**
     * Поиск по документам
     */
    fun search(query: String) {
        documents.value?.clear()
        loadData(query)
    }

    /**
     * Запрос данных из интерактора
     */
    private fun loadData(
        search: String = "",
        limit: Int = DEFAULT_LIMIT,
        offset: Int = DEFAULT_OFFSET
    ) {
        val loadDataRequest = documentsInteractor.getDocuments(search, limit, offset)
        val observable = documentsInteractor.isDatabaseEmpty()
            .flatMap {
                if (it) {
                    parserInteractor.parseDocuments(libraryInputStream).flatMap { loadDataRequest }
                } else {
                    loadDataRequest
                }
            }

        subscribe(observable, {
            paginationState.postValue(it.getState())
            documents.postValue(documents.value?.mergeIfNeed(it))
        }, {
            paginationState.postValue(PaginationState.ERROR)
        })
    }
}