package seifemadhamdy.mazaadyportal.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import seifemadhamdy.mazaadyportal.R
import seifemadhamdy.mazaadyportal.data.remote.api.RetrofitClient
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.data.repository.CategoriesRepositoryImpl
import seifemadhamdy.mazaadyportal.data.repository.OptionsRepositoryImpl
import seifemadhamdy.mazaadyportal.data.repository.PropertiesRepositoryImpl
import seifemadhamdy.mazaadyportal.databinding.FragmentFormBinding
import seifemadhamdy.mazaadyportal.ui.viewmodels.FormViewModel
import seifemadhamdy.mazaadyportal.ui.viewmodels.FormViewModelFactory

class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var viewModel: FormViewModel
    private val apiService by lazy { RetrofitClient.apiService }
    private val categoriesRepository by lazy { CategoriesRepositoryImpl(apiService = apiService) }
    private val propertiesRepository by lazy { PropertiesRepositoryImpl(apiService = apiService) }
    private val optionsRepository by lazy { OptionsRepositoryImpl(apiService = apiService) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(
                this,
                FormViewModelFactory(
                    categoriesRepository = categoriesRepository,
                    propertiesRepository = propertiesRepository,
                ),
            )[FormViewModel::class.java]

        viewModel.mainCategories.observe(
            viewLifecycleOwner,
            Observer { categories ->
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        categories.map { it.name },
                    )
                binding.mainCategoryAutoCompleteTextView.setAdapter(adapter)
            },
        )

        viewModel.subCategories.observe(
            viewLifecycleOwner,
            Observer { subCategories ->
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        subCategories.map { it.name },
                    )
                binding.subCategoryAutoCompleteTextView.setAdapter(adapter)
            },
        )

        viewModel.subCategoryProperties.observe(
            viewLifecycleOwner,
            Observer { subCategoryProperties ->
                binding.dynamicPropertiesContainer.removeAllViews()

                subCategoryProperties.forEach { property -> setupPropertyView(property = property) }
            },
        )

        viewModel.fetchMainCategories()

        binding.mainCategoryAutoCompleteTextView.setOnItemClickListener { parent, view, position, id
            ->
            val selectedCategory = viewModel.mainCategories.value?.get(position)
            binding.subCategoryAutoCompleteTextView.setText("")
            binding.subCategoryAutoCompleteTextView.setAdapter(null)
            binding.dynamicPropertiesContainer.removeAllViews()
            selectedCategory?.let { viewModel.updateSubCategories(it.children) }
        }

        binding.subCategoryAutoCompleteTextView.setOnItemClickListener { parent, view, position, id
            ->
            val selectedSubCategory = viewModel.subCategories.value?.get(position)
            selectedSubCategory?.id?.let { viewModel.fetchPropertiesBySubCategoryId(it) }
        }

        binding.submitButton.setOnClickListener {
            val mainCategoryId =
                viewModel.mainCategories.value
                    ?.find { binding.mainCategoryAutoCompleteTextView.text.toString() == it.name }
                    ?.id
            val subCategoryId =
                viewModel.subCategories.value
                    ?.find { binding.subCategoryAutoCompleteTextView.text.toString() == it.name }
                    ?.id

            if (mainCategoryId != null && subCategoryId != null)
                setupTable(mainCategoryValue = mainCategoryId, subCategoryValue = subCategoryId)
        }
    }

    private fun setupTable(mainCategoryValue: Int, subCategoryValue: Int) {
        binding.resultsTable.apply {
            removeAllViews()

            addView(createTableRow("Key", "Value", true))
            addView(createTableRow("Main Category", mainCategoryValue.toString()))
            addView(createTableRow("Sub Category", subCategoryValue.toString()))
        }
    }

    private fun createTableRow(
        property: String,
        value: String,
        isHeader: Boolean = false,
    ): TableRow {
        return TableRow(context).apply {
            layoutParams =
                TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                )

            addView(
                TextView(context).apply {
                    text = property
                    setPadding(16, 16, 16, 16)
                    setTypeface(null, if (isHeader) Typeface.BOLD else Typeface.NORMAL)

                    if (isHeader) {
                        setBackgroundResource(R.color.color_EC5F5F)
                        setTextColor(Color.WHITE)
                    } else {
                        setBackgroundResource(R.color.color_EEEEEE)
                        setTextColor(Color.BLACK)
                    }
                }
            )

            addView(
                TextView(context).apply {
                    text = value
                    setPadding(16, 16, 16, 16)
                    setTypeface(null, if (isHeader) Typeface.BOLD else Typeface.NORMAL)
                    if (isHeader) {
                        setBackgroundResource(R.color.color_FCD034)
                        setTextColor(Color.WHITE)
                    } else {
                        setBackgroundResource(R.color.color_EEEEEE)
                        setTextColor(Color.BLACK)
                    }
                }
            )

            if (!isHeader) {
                background =
                    GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setStroke(1, Color.LTGRAY)
                    }
            }
        }
    }

    private fun setupPropertyView(
        parentViewId: Int? = null,
        property: PropertiesDataDto,
        injectionIndex: Int? = null,
    ) {
        val linearLayout =
            LinearLayout(context).apply {
                tag = parentViewId
                orientation = LinearLayout.VERTICAL
                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
            }

        val textInputLayout =
            TextInputLayout(ContextThemeWrapper(requireContext(), R.style.ExposedDropDownStyle))
                .apply {
                    id = View.generateViewId()
                    layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                        )
                    hint = property.name
                }

        linearLayout.addView(textInputLayout)

        val autoCompleteTextView =
            AutoCompleteTextView(context).apply {
                layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
            }

        textInputLayout.addView(autoCompleteTextView)
        val options = listOf(resources.getString(R.string.other)) + property.options.map { it.name }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)

        autoCompleteTextView.apply {
            setAdapter(adapter)

            setOnItemClickListener { parent, view, position, id ->
                for (child in binding.dynamicPropertiesContainer.children) {
                    if (child.tag == textInputLayout.id)
                        binding.dynamicPropertiesContainer.removeView(child)
                }

                val selectedOption = options[position]

                if (selectedOption == resources.getString(R.string.other)) {
                    val otherEditText =
                        EditText(context).apply {
                            hint = resources.getString(R.string.specify_here)
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                )
                        }

                    linearLayout.addView(otherEditText)
                } else {
                    val selectedProperty = property.options.find { it.name == selectedOption }

                    if (selectedProperty?.child == true) {
                        val currentIndex =
                            binding.dynamicPropertiesContainer.indexOfChild(linearLayout)
                        selectedProperty.id?.let {
                            fetchChildOptions(textInputLayout.id, it, currentIndex + 1)
                        }
                    }
                }
            }
        }

        if (injectionIndex == null) {
            binding.dynamicPropertiesContainer.addView(linearLayout)
        } else {
            binding.dynamicPropertiesContainer.addView(linearLayout, injectionIndex)
        }
    }

    private fun fetchChildOptions(parentViewId: Int?, optionId: Int, injectionIndex: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.IO) {
                val childOptions = optionsRepository.getOptionsChildByOptionId(optionId = optionId)
                withContext(Dispatchers.Default) {
                    childOptions?.forEach { childProperty ->
                        withContext(Dispatchers.Main) {
                            setupPropertyView(parentViewId, childProperty, injectionIndex)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
