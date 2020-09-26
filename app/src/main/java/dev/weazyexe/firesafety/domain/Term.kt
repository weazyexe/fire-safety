package dev.weazyexe.firesafety.domain

data class Term(
    val id: Int,
    val title: String,
    val definition: String,
    val link: String,
    val isFavorite: Boolean
)