package dev.weazyexe.firesafety.app.data.network.term.response

import com.google.gson.annotations.SerializedName
import dev.weazyexe.firesafety.app.base.Transformable
import dev.weazyexe.firesafety.app.data.network.MetadataObj
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.utils.extensions.transform
import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList

/**
 * JSON представление термина, приходящее с сервера
 */
data class TermObj(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("definition")
    val definition: String,

    @SerializedName("link")
    val link: String
) : Transformable<Term> {
    override fun transform(): Term {
        return Term(id, title, definition, link, false)
    }
}

/**
 * Респонс на GET запрос /definitions
 */
data class TermsResponse(
    @SerializedName("results")
    val items: List<TermObj>,

    @SerializedName("metadata")
    val metadata: MetadataObj

) : Transformable<DataList<Term>> {
    override fun transform(): DataList<Term> {
        return DataList(
            items.transform(),
            metadata.limit,
            metadata.offset,
            metadata.totalCount
        )
    }
}