package dev.weazyexe.firesafety.interactor

import dev.weazyexe.firesafety.domain.Term
import io.reactivex.rxjava3.core.Observable
import java.io.InputStream
import javax.inject.Inject

/**
 * Интерактор для переноса данных CSV на сервер
 */
class ParserInteractor @Inject constructor(
    private val termsInteractor: TermsInteractor
) {

    /**
     * Парсинг csv файла и запись на сервер
     *
     * @param inputStream поток данных csv файла
     * @return возвращает [Observable] с [Int] значением, обозначающим прогресс выполнения
     */
    fun parse(inputStream: InputStream): Observable<Int> = Observable.create<Int> { emitter ->
        val rawTerms = getRawData(inputStream)
        val terms = rawTerms.map { parseTerm(it.split(';')) }

        emitter.onNext(terms.size)

        terms.forEachIndexed { index, term ->
            termsInteractor.createTerm(term.title, term.definition, term.link).subscribe({
                emitter.onNext(index)
            }, {})
        }
    }


    /**
     * Получение данных из
     *
     * @param inputStream поток данных csv файла
     */
    private fun getRawData(inputStream: InputStream): List<String> {
        val read = String(inputStream.readBytes())
        val rawTerms = read.split('\n')
        return rawTerms.subList(1, rawTerms.size)
    }

    /**
     * Парсинг одной строки из csv файла
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
}