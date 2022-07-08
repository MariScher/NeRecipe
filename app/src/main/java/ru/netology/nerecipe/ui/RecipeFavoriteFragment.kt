package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.ListFavoriteBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class RecipeFavoriteFragment : Fragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ListFavoriteBinding.inflate(layoutInflater, container, false).also { binding ->

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val favRecipes = recipes.none { it.isFavorite }
            if (favRecipes) {
                binding.textBackground.isVisible = favRecipes
                binding.iconBackground.isVisible = favRecipes
            }
        }

        val adapter = RecipeAdapter(viewModel)
        binding.listFavorite.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val favRecipes = recipes.filter { it.isFavorite }
            adapter.submitList(favRecipes)
        }

        binding.bottomToolbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> findNavController().popBackStack()
            }
            true
        }

        binding.bottomToolbar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    viewModel.favoriteFragment.call()
                    true
                }
                R.id.filter -> {
                    viewModel.filterFragment.call()
                    true
                }
                R.id.feed -> {
                    viewModel.feedFragment.observe(viewLifecycleOwner) {
                        val directions =
                            RecipeFavoriteFragmentDirections.actionFavoriteFragmentToFeedFragment()
                        findNavController().navigate(directions)
                    }
                    true
                }
                else -> false
            }
        }
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateRecipeFragment.observe(this) {
            val updatedRecipe = viewModel.updateRecipe.value
            val directions = FeedRecipeFragmentDirections.updateRecipeFragment(updatedRecipe)
            findNavController().navigate(directions)
        }

        viewModel.singleFragment.observe(this) {
            val viewRecipe = viewModel.singleRecipe.value
            val directions = FeedRecipeFragmentDirections.recipeViewFragment(viewRecipe)
            findNavController().navigate(directions)
        }

        viewModel.filterFragment.observe(this) {
            val directions = FeedRecipeFragmentDirections.recipeFilterFragment()
            findNavController().navigate(directions)
        }
    }
}