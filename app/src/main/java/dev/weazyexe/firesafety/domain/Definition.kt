package dev.weazyexe.firesafety.domain

data class Definition(
    val id: String,
    val title: String,
    val definition: String,
    val link: String,
    val isFavorite: Boolean
)