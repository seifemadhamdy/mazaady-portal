package seifemadhamdy.mazaadyportal.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import seifemadhamdy.mazaadyportal.R
import seifemadhamdy.mazaadyportal.data.remote.api.RetrofitClient
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesOptionsDto
import seifemadhamdy.mazaadyportal.data.remote.network.NetworkConnectivityService
import seifemadhamdy.mazaadyportal.data.repository.api.CategoriesRepositoryImpl
import seifemadhamdy.mazaadyportal.data.repository.api.OptionsRepositoryImpl
import seifemadhamdy.mazaadyportal.data.repository.api.PropertiesRepositoryImpl
import seifemadhamdy.mazaadyportal.data.repository.network.NetworkConnectivityRepositoryImpl
import seifemadhamdy.mazaadyportal.databinding.FragmentFormBinding
import seifemadhamdy.mazaadyportal.ui.models.SelectedPropertyItem
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
    private val networkConnectivityRepository by lazy {
        NetworkConnectivityRepositoryImpl(
            networkConnectivityService = NetworkConnectivityService(context = requireContext())
        )
    }
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
                    networkConnectivityRepository = networkConnectivityRepository,
                ),
            )[FormViewModel::class.java]

        viewModel.isNetworkAvailable.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                viewModel.fetchMainCategories()
            } else {
                resetUi()
                Toast.makeText(context, "Network is unavailable.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.mainCategories.observe(viewLifecycleOwner) { categories ->
            val adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    categories.map { it.name },
                )

            binding.mainCategoryAutoCompleteTextView.setAdapter(adapter)
            binding.mainCategoryTextInputLayout.isEnabled = true
        }

        viewModel.subCategories.observe(viewLifecycleOwner) { subCategories ->
            val adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    subCategories.map { it.name },
                )

            binding.subCategoryAutoCompleteTextView.setAdapter(adapter)
            binding.subCategoryTextInputLayout.isEnabled = true
        }

        viewModel.subCategoryProperties.observe(viewLifecycleOwner) { subCategoryProperties ->
            binding.dynamicPropertiesContainer.removeAllViews()

            subCategoryProperties.forEach { property -> setupPropertyView(property = property) }
        }

        viewModel.selectedProperties.observe(viewLifecycleOwner) { selectedProperties ->
            if (binding.resultsTable.visibility != View.GONE)
                binding.resultsTable.visibility = View.GONE

            if (selectedProperties.isNotEmpty()) {
                updateTable(selectedProperties = selectedProperties)
                binding.submitButton.isEnabled = true
            }
        }

        binding.mainCategoryAutoCompleteTextView.setOnItemClickListener { parent, view, position, id
            ->
            resetSubCategory()
            binding.dynamicPropertiesContainer.removeAllViews()
            val selectedCategory = viewModel.mainCategories.value?.get(position)
            selectedCategory?.let { viewModel.updateSubCategories(it.children) }
        }

        binding.subCategoryAutoCompleteTextView.setOnItemClickListener { parent, view, position, id
            ->
            val selectedSubCategory = viewModel.subCategories.value?.get(position)
            selectedSubCategory?.id?.let { viewModel.fetchPropertiesBySubCategoryId(it) }
        }

        binding.submitButton.setOnClickListener { binding.resultsTable.visibility = View.VISIBLE }
    }

    private fun resetMainCategory() {
        binding.mainCategoryAutoCompleteTextView.setText("")
        binding.mainCategoryAutoCompleteTextView.setAdapter(null)
        viewModel.resetMainCategories()
    }

    private fun resetSubCategory() {
        binding.subCategoryAutoCompleteTextView.setText("")
        binding.subCategoryAutoCompleteTextView.setAdapter(null)
        viewModel.resetSubCategories()
    }

    private fun resetUi() {
        binding.mainCategoryTextInputLayout.apply {
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = false
        }

        resetMainCategory()

        binding.mainCategoryAutoCompleteTextView.apply {
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = false
            setOnClickListener(null)
        }

        binding.subCategoryTextInputLayout.apply {
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = false
        }

        resetSubCategory()

        binding.subCategoryAutoCompleteTextView.apply {
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = false
            setOnClickListener(null)
        }

        binding.dynamicPropertiesContainer.removeAllViews()
        binding.submitButton.isEnabled = false
        viewModel.resetSelectedProperties()

        if (binding.resultsTable.visibility != View.GONE)
            binding.resultsTable.visibility = View.GONE
    }

    private fun updateTable(selectedProperties: List<SelectedPropertyItem>) {
        binding.resultsTable.removeAllViews()

        binding.resultsTable.apply {
            addView(createTableRow(property = "Property", value = "Value", isHeader = true))

            addView(
                createTableRow(
                    property = binding.mainCategoryTextInputLayout.hint.toString(),
                    value = binding.mainCategoryAutoCompleteTextView.text.toString(),
                )
            )

            addView(
                createTableRow(
                    property = binding.subCategoryTextInputLayout.hint.toString(),
                    value = binding.subCategoryAutoCompleteTextView.text.toString(),
                )
            )
        }

        selectedProperties.forEach { item ->
            binding.resultsTable.addView(
                createTableRow(item.parentName, item.selectedProperty.name.toString())
            )
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
                    gravity = Gravity.CENTER
                    setPadding(16, 16, 16, 16)
                    setTypeface(null, if (isHeader) Typeface.BOLD else Typeface.NORMAL)

                    if (isHeader) {
                        setBackgroundResource(R.color.color_EC5F5F)
                        setTextColor(Color.WHITE)
                    } else {
                        background =
                            GradientDrawable().apply {
                                shape = GradientDrawable.RECTANGLE
                                setStroke(1, Color.BLACK)
                            }

                        setTextColor(Color.BLACK)
                    }
                }
            )

            addView(
                TextView(context).apply {
                    text = value
                    gravity = Gravity.CENTER
                    setPadding(16, 16, 16, 16)
                    setTypeface(null, if (isHeader) Typeface.BOLD else Typeface.NORMAL)
                    if (isHeader) {
                        setBackgroundResource(R.color.color_FCD034)
                        setTextColor(Color.WHITE)
                    } else {
                        background =
                            GradientDrawable().apply {
                                shape = GradientDrawable.RECTANGLE
                                setStroke(1, Color.BLACK)
                            }

                        setTextColor(Color.BLACK)
                    }
                }
            )
        }
    }

    private fun setupPropertyView(parentViewId: Int? = null, property: PropertiesDataDto) {
        val linearLayout =
            LinearLayout(context).apply {
                id = parentViewId ?: View.generateViewId()
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

            setOnItemClickListener { _, _, position, _ ->
                while (linearLayout.childCount > 1) {
                    linearLayout.removeViewAt(1)
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

                    otherEditText.doOnTextChanged { text, _, _, _ ->
                        viewModel.updateSelectedProperties(
                            selectedPropertyItem =
                                SelectedPropertyItem(
                                    parentName = textInputLayout.hint.toString(),
                                    selectedProperty = PropertiesOptionsDto(name = text.toString()),
                                )
                        )
                    }

                    linearLayout.addView(otherEditText)
                } else {
                    val selectedProperty = property.options.find { it.name == selectedOption }

                    selectedProperty?.let { selectedProperty ->
                        viewModel.updateSelectedProperties(
                            selectedPropertyItem =
                                SelectedPropertyItem(
                                    parentName = textInputLayout.hint.toString(),
                                    selectedProperty = selectedProperty,
                                )
                        )

                        if (
                            selectedProperty.child == true &&
                                viewModel.isNetworkAvailable.value == true
                        ) {
                            parentViewId?.let { parentViewId ->
                                val parentLayout =
                                    binding.dynamicPropertiesContainer.findViewById<LinearLayout>(
                                        parentViewId
                                    )

                                val parentChildCount = parentLayout.childCount

                                val currentViewIndex =
                                    (0 until parentChildCount).firstOrNull {
                                        parentLayout.getChildAt(it).id == linearLayout.id
                                    }

                                if (currentViewIndex != null) {
                                    while (parentLayout.childCount > currentViewIndex + 1) {
                                        parentLayout.removeViewAt(currentViewIndex + 1)
                                    }
                                }
                            }

                            selectedProperty.id?.let { fetchChildOptions(linearLayout.id, it) }
                        }
                    }
                }
            }
        }

        if (parentViewId == null) {
            binding.dynamicPropertiesContainer.addView(linearLayout)
        } else {
            binding.dynamicPropertiesContainer
                .findViewById<LinearLayout>(parentViewId)
                .addView(linearLayout)
        }
    }

    private fun fetchChildOptions(parentViewId: Int?, optionId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val childOptions =
                withContext(Dispatchers.IO) {
                    optionsRepository.getOptionsChildByOptionId(optionId = optionId)
                }
            childOptions?.forEach { childProperty ->
                setupPropertyView(parentViewId, childProperty)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
