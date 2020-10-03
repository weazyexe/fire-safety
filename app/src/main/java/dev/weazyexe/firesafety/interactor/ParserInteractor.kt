package dev.weazyexe.firesafety.interactor

import dev.weazyexe.firesafety.domain.Document
import dev.weazyexe.firesafety.domain.Term
import io.reactivex.rxjava3.core.Observable
import java.io.InputStream
import javax.inject.Inject

/**
 * Интерактор для переноса данных CSV на сервер
 */
class ParserInteractor @Inject constructor(
    private val termsInteractor: TermsInteractor,
    private val documentsInteractor: DocumentsInteractor
) {

    /**
     * Парсинг csv файла с терминами и запись на сервер
     *
     * @param inputStream поток данных csv файла
     * @return возвращает [Observable] с [Int] значением, обозначающим прогресс выполнения
     */
    @Deprecated("Sorry, but you can not using it without auth token")
    fun parseTerms(inputStream: InputStream): Observable<Int> = Observable.create { emitter ->
        val rawTerms = getRawTermsData(inputStream)
        val terms = rawTerms.map { parseTerm(it.split(';')) }

        emitter.onNext(terms.size)

        terms.forEachIndexed { index, term ->
            termsInteractor.createTerm(term.title, term.definition, term.link).subscribe({
                emitter.onNext(index)
            }, {})
        }
    }

    /**
     * Парсинг csv файла с документами и запись в БД
     */
    fun parseDocuments(inputStream: InputStream) = Observable.create<Boolean> { emitter ->
        try {
            val rawDocuments = getRawDocumentsData(inputStream)
            val documents = rawDocuments.map {
                parseDocument(
                    it.split("\";", ");").filter { str -> str.isNotBlank() }
                )
            }

            documents.forEachIndexed { index, doc ->
                documentsInteractor.createDocument(doc.copy(id = index + 1))
            }

            emitter.onNext(true)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }

    /**
     * Получение данных из [InputStream]
     *
     * @param inputStream поток данных csv файла
     */
    private fun getRawTermsData(inputStream: InputStream): List<String> {
        val read = String(inputStream.readBytes())
        val rawTerms = read.split('\n')
        return rawTerms.subList(1, rawTerms.size)
    }

    /**
     * Получение сырых данных о документах из [InputStream]
     */
    private fun getRawDocumentsData(inputStream: InputStream): List<String> {
        val read = String(inputStream.readBytes())
        return read.split('\n')
    }

    /**
     * Парсинг одной строки из csv файла с терминами
     *
     * @param columns колонки из csv файла (0 - термин, 1 - определение)
     */
    private fun parseTerm(columns: List<String>): Term {
        val title = columns[0]  // вытаскиваем термин
        val rawDefinition =
            columns[1].subSequence(2, columns[1].length)   // убираем тире вначале определения

        // далее требуется отделить ссылку от определения

        val words = rawDefinition.split(' ')    // пилим всё определение на слова по пробелу
        val stopWords = listOf( // стоп-слова, с которых начинается ссылка
            Regex("п\\."),
            Regex("ст\\."),
            Regex("гл\\."),
            Regex("ГОСТ"),
            Regex("ФЗ-.*"),
            Regex("СП")
        )

        var definition = ""
        var link = ""

        // немного магии
        for ((index, word) in words.withIndex()) {
            for (regex in stopWords) {
                if (word.matches(regex)) {
                    definition = words.subList(0, index).joinToString(" ")
                    link = words.subList(index, words.size).joinToString(" ")
                    break
                }
            }
            if (definition.isNotEmpty() && link.isNotEmpty()) {
                break
            }
        }

        // и готово
        return Term(0, title, definition, link, false)
    }

    /**
     * Парсинг одной строки из csv файла с документами
     *
     * @param columns колонки из csv файла (0 - название документа, 1 - ссылка)
     */
    private fun parseDocument(columns: List<String>): Document {
        // Добавляем в конце кавычку, потому что делали сплит с помощью неё
        val title = columns[0] + "\""

        // Ссылка находится в формате "N. https://example.com"
        // Требуется вытащить из этой строки только адрес
        // Их разделяет пробел
        // columns.size < 2 && columns[1].split(" ").size != 2
        val link = columns[1].split(" ")[1]

        return Document(0, title, link)
    }
}