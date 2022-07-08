package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.databinding.FragmentFilterBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class RecipeFilterFragment : Fragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilterBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.buttonApply.setOnClickListener {
            onApplyButtonClicked(binding)
        }
    }.root

    private fun onApplyButtonClicked(binding: FragmentFilterBinding) {
        var checkedCount = 0
        val numberOfFilters = 7

        if (!binding.checkBoxEuropean.isChecked) {
            ++checkedCount
            viewModel.showEuropean("European")
        }
        if (!binding.checkBoxAsian.isChecked) {
            ++checkedCount
            viewModel.showAsian("Asian")

        }
        if (!binding.checkBoxPanasian.isChecked) {
            ++checkedCount
            viewModel.showPanasian("Panasian")

        }
        if (!binding.checkBoxEastern.isChecked) {
            ++checkedCount
            viewModel.showEastern("Eastern")
        }
        if (!binding.checkBoxAmerican.isChecked) {
            ++checkedCount
            viewModel.showAmerican("American")
        }
        if (!binding.checkBoxRussian.isChecked) {
            ++checkedCount
            viewModel.showRussian("Russian")
        }
        if (!binding.checkBoxMediterranean.isChecked) {
            ++checkedCount
            viewModel.showMediterranean("Mediterranean")
        }

        if (checkedCount == numberOfFilters) {
            Toast.makeText(activity, "You cannot disable all filters", Toast.LENGTH_LONG).show()
            return
        } else findNavController().popBackStack()
    }
}