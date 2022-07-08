package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.databinding.RecipeBinding

class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener

) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.option_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavorite.setImageResource(getFavoriteIconResId(recipe.isFavorite))
                buttonFavorite.setOnClickListener {
                    interactionListener.onFavoriteClicked(recipe.id)
                }
                title.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                textRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                authorName.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                categoryRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                menuOptions.setOnClickListener {
                    popupMenu.show()
                }
            }
        }

        @DrawableRes
        private fun getFavoriteIconResId(liked: Boolean) =
            if (liked) R.drawable.icon_is_favorites else R.drawable.icon_is_not_favorites
    }
}

private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem == newItem
}