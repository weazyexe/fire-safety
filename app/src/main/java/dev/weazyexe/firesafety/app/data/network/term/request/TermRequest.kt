package dev.weazyexe.firesafety.app.data.network.term.request

import com.google.gson.annotations.SerializedName

/**
 * Сущность для реквеста создания термина
 */
data class TermRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("definition")
    val definition: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("token")
    val token: String
)