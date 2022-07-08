package ru.netology.nerecipe.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val categoryRecipe: String,
    val textRecipe: String,
    val isFavorite: Boolean = false,
) : Parcelable