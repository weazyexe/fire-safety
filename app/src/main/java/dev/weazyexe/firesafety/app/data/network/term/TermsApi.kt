package dev.weazyexe.firesafety.app.data.network.term

import dev.weazyexe.firesafety.app.data.network.term.request.TermRequest
import dev.weazyexe.firesafety.app.data.network.term.response.TermsResponse
import dev.weazyexe.firesafety.utils.DEFAULT_LIMIT
import dev.weazyexe.firesafety.utils.DEFAULT_OFFSET
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Интерфейс для работы с API терминов
 */
interface TermsApi {

    /**
     * Получение списка терминов
     */
    @GET("definitions")
    fun getTerms(
        @Query("search") search: String = "",
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET
    ): Observable<TermsResponse>

    /**
     * Создание термина
     */
    @Deprecated("Sorry, but you can not using it without auth token")
    @POST("definitions")
    fun createTerm(@Body request: TermRequest): Completable
}