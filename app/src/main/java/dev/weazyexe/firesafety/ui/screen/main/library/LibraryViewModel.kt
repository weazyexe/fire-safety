package dev.weazyexe.firesafety.ui.screen.main.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.weazyexe.firesafety.app.App
import dev.weazyexe.firesafety.interactor.DocumentsInteractor
import dev.weazyexe.firesafety.interactor.ParserInteractor
import dev.weazyexe.firesafety.utils.extensions.subscribe
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

    val state = MutableLiveData<Boolean?>()

    init {
        App.component.inject(this)
    }

    /**
     * Заполнение базы данных документов данными из csv файла
     *
     * @param inputStream [InputStream] csv файла из ассетов
     */
    fun fillDatabaseIfNeedAndGetData(inputStream: InputStream) {

        val observable = documentsInteractor.isDatabaseEmpty()
            .flatMap {
                if (it) {
                    parserInteractor.parseDocuments(inputStream)
                        .flatMap { documentsInteractor.getDocuments() }
                } else {
                    documentsInteractor.getDocuments()
                }
            }

        subscribe(observable, { documents ->
            if (documents.isNotEmpty()) {
                state.postValue(true)
            } else {
                throw Throwable("wtf rly?")
            }
        }, {
            state.postValue(false)
        })
    }
}