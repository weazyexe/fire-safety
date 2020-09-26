package dev.weazyexe.firesafety.interactor

import dev.weazyexe.firesafety.app.data.repository.TermsRepository
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import javax.inject.Inject

/**
 * Интерактор с логикой получения терминов
 */
class TermsInteractor @Inject constructor(
    private val termsRepository: TermsRepository
) {

    /**
     * Получение терминов из репозитория
     */
    fun getTerms(
        search: String = "",
        limit: Int = DEFAULT_LIMIT,
        offset: Int = DEFAULT_OFFSET,
        forceNetwork: Boolean = false
    ): Observable<DataList<Term>> = termsRepository.getTerms(search, limit, offset, forceNetwork)

    /**
     * Создание термина в сети
     */
    @Deprecated("Sorry, but you can not using it without auth token")
    fun createTerm(title: String, definition: String, link: String): Completable =
        termsRepository.createTerm(title, definition, link)
}