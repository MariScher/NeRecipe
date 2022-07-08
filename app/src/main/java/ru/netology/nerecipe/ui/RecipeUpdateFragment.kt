package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.FragmentUpdateBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class RecipeUpdateFragment : Fragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()
    private val args by navArgs<RecipeUpdateFragmentArgs>()
    private var categoryRecipeNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUpdateBinding.inflate(layoutInflater, container, false).also { binding ->
        incomingArg(binding)

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.checkBoxEuropean -> categoryRecipeNumber = "European"
                R.id.checkBoxAsian -> categoryRecipeNumber = "Asian"
                R.id.checkBoxPanasian -> categoryRecipeNumber = "Panasian"
                R.id.checkBoxEastern -> categoryRecipeNumber = "Eastern"
                R.id.checkBoxAmerican -> categoryRecipeNumber = "American"
                R.id.checkBoxRussian -> categoryRecipeNumber = "Russian"
                R.id.checkBoxMediterranean -> categoryRecipeNumber = "Mediterranean"
            }
        }
        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }
    }.root

    private fun onSaveButtonClicked(binding: FragmentUpdateBinding) {
        val id = args.idRecipe!!.id
        val title = binding.title.text.toString()
        val authorName = binding.authorName.text.toString()
        val textRecipe = binding.textRecipe.text.toString()

        if (!emptyCheckUpdateWarning(
                title = title,
                authorName = authorName,
                textRecipe = textRecipe,
                categoryRecipe = categoryRecipeNumber
            )
        ) return
        viewModel.updateContent(
            id = id,
            title = title,
            authorNam = authorName,
            textRecipe = textRecipe,
            categoryRecipe = categoryRecipeNumber
        )
        findNavController().popBackStack()
    }

    private fun incomingArg(binding: FragmentUpdateBinding) {
        binding.title.setText(args.idRecipe?.title)
        binding.authorName.setText(args.idRecipe?.authorName)
        binding.textRecipe.setText(args.idRecipe?.textRecipe)
    }

    private fun emptyCheckUpdateWarning(
        title: String,
        authorName: String,
        textRecipe: String,
        categoryRecipe: String
    ): Boolean {
        return if (title.isBlank() || authorName.isBlank() || textRecipe.isBlank() || categoryRecipe.isBlank()) {
            Toast.makeText(activity, "All fields must be filled in", Toast.LENGTH_LONG).show()
            false
        } else true
    }
}