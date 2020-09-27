package dev.weazyexe.firesafety.ui.screen.main.terms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.weazyexe.firesafety.app.App
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.interactor.TermsInteractor
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import dev.weazyexe.firesafety.utils.extensions.default
import dev.weazyexe.firesafety.utils.extensions.getState
import dev.weazyexe.firesafety.utils.extensions.mergeIfNeed
import dev.weazyexe.firesafety.utils.extensions.subscribe
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import javax.inject.Inject

/**
 * ViewModel с логикой получения и хранения списка терминов с пагинацией
 */
class TermsViewModel : ViewModel() {

    @Inject
    lateinit var termsInteractor: TermsInteractor

    init {
        App.component.inject(this)
    }

    val terms = MutableLiveData<DataList<Term>>().default(DataList.empty())
    val paginationState = MutableLiveData<PaginationState>().default(PaginationState.READY)

    /**
     * Загрузка терминов
     */
    fun loadTerms(search: String = "", limit: Int = DEFAULT_LIMIT, offset: Int = DEFAULT_OFFSET) {
        subscribe(termsInteractor.getTerms(search, limit, offset, false), {
            paginationState.postValue(it.getState())
            terms.postValue(terms.value?.mergeIfNeed(it))
        }, {
            paginationState.postValue(PaginationState.ERROR)
        })
    }

    /**
     * Загрузка следующей порции терминов
     */
    fun loadMore() = loadTerms(offset = terms.value?.nextOffset ?: DEFAULT_OFFSET)

    /**
     * При нажатии на айтем в списке
     */
    fun onTermClick(term: Term) {
        // TODO
    }
}