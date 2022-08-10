package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.dto.Category
import ru.netology.nerecipe.dto.Recipe

object InMemoryRecipeRepositoryImpl : RecipeRepository {
    private var nextId = 1L

    private var recipes = listOf(
        Recipe(
            id = nextId++,
            title = "Суп",
            authorName = "Я",
            categoryRecipe = Category.Russian,
            textRecipe = "Всё кинули и варим",
            isFavorite = false
        ),
        Recipe(
            id = nextId++,
            title = "Борщ",
            authorName = "Я",
            categoryRecipe = Category.Russian,
            textRecipe = "Всё кинули и варим",
            isFavorite = false
        )
    )

    override val data = MutableLiveData(recipes)

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_ID) insert(recipe) else update(recipe)
    }

    override fun delete(recipeId: Long) {
        recipes = recipes.filter { it.id != recipeId }
        data.value = recipes
    }

    override fun toFavorite(recipeId: Long) {
        recipes =
            recipes.map {
                if (it.id == recipeId)
                    it.copy(isFavorite = !it.isFavorite)
                else it
            }
        data.value = recipes
    }

    override fun search(recipeName: String) {
        recipes.find {
            it.authorName == recipeName
        } ?: throw RuntimeException("Ничего не найдено")
        data.value = recipes
    }

    override fun getCategory(category: Category) {
        recipes.find {
            it.categoryRecipe == category
        }
        data.value = recipes
    }

    override fun update() {
        data.value = recipes
    }

    private fun update(recipe: Recipe) {
        recipes = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
        data.value = recipes
    }

    private fun insert(recipe: Recipe) {
        recipes =
            listOf(
                recipe.copy(
                    id = nextId++
                )
            ) + recipes
        data.value = recipes
    }
}