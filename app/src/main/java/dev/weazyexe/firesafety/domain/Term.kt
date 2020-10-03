package dev.weazyexe.firesafety.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Term(
    val id: Int,
    val title: String,
    val definition: String,
    val link: String,
    val isFavorite: Boolean
): Parcelable