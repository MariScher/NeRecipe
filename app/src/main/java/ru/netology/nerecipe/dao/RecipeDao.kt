package ru.netology.nerecipe.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nerecipe.dto.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert
    fun save(recipe: RecipeEntity)

    @Query(
        "UPDATE recipes SET " +
                "title = :title," +
                "authorName = :authorName, " +
                "categoryRecipe = :categoryRecipe," +
                "textRecipe = :textRecipe" +
                " WHERE id = :id"
    )
    fun updateContentById(
        id: Long, title: String, authorName: String,
        categoryRecipe: String, textRecipe: String
    )

    @Query(
        """
        UPDATE recipes SET
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun favById(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT * FROM recipes WHERE categoryRecipe = :categoryRecipe")
    fun getEuropean(categoryRecipe: String): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :text || '%'")
    fun searchByText(text: String): LiveData<List<RecipeEntity>>
}