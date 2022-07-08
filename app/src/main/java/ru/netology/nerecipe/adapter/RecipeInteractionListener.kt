package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.dto.Recipe

interface RecipeInteractionListener {
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onFavoriteClicked(recipeId: Long)
    fun onSearchClicked(text: String)
    fun onCreateClicked()
    fun updateContent(
        id: Long,
        title: String,
        authorNam: String,
        categoryRecipe: String,
        textRecipe: String
    )

    fun onSaveClicked(title: String, authorNam: String, categoryRecipe: String, textRecipe: String)
    fun onSingleRecipeClicked(recipe: Recipe)
}