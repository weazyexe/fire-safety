package dev.weazyexe.firesafety.app.data.repository

import dev.weazyexe.firesafety.app.data.network.term.TermsApi
import dev.weazyexe.firesafety.app.data.network.term.request.TermRequest
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import javax.inject.Inject

/**
 * Репозиторий для получения терминов из БД или из сети
 */
class TermsRepository @Inject constructor(
    private val termsApi: TermsApi
) {

    /**
     * Получение терминов из БД или из сети
     * TODO: добавить чтение из БД
     *
     * @param search поисковой параметр
     * @param forceNetwork принудительно загружать данные из сети
     */
    fun getTerms(
        search: String,
        limit: Int,
        offset: Int,
        forceNetwork: Boolean
    ): Observable<DataList<Term>> =
        termsApi.getTerms(search, limit, offset).map { it.transform() }

    /**
     * Создание термина на сервере
     *
     * @param title термин
     * @param definition определение термина
     * @param link ссылка на термин в ГОСТах
     */
    fun createTerm(title: String, definition: String, link: String): Completable =
        termsApi.createTerm(TermRequest(title, definition, link))
}