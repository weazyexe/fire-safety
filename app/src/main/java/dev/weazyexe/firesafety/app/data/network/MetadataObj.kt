package dev.weazyexe.firesafety.app.data.network

import com.google.gson.annotations.SerializedName

/**
 * Метаданные с информацией о пагинации
 */
data class MetadataObj(
    @SerializedName("limit")
    val limit: Int,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("total_count")
    val totalCount: Int
)