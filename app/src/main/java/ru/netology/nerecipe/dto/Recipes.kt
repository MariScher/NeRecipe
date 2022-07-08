package ru.netology.nerecipe.dto

internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    title = title,
    authorName = authorName,
    categoryRecipe = categoryRecipe,
    textRecipe = textRecipe,
    isFavorite = isFavorite,
)

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    title = title,
    authorName = authorName,
    categoryRecipe = categoryRecipe,
    textRecipe = textRecipe,
    isFavorite = isFavorite,
)