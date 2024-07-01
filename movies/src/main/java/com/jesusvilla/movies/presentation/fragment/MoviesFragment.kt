package com.jesusvilla.movies.presentation.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.base.ui.BaseFragment
import com.jesusvilla.base.ui.getInsetDividerItemDecoration
import com.jesusvilla.movies.R
import com.jesusvilla.movies.databinding.FragmentMoviesBinding
import com.jesusvilla.movies.presentation.adapter.MoviesAdapter
import com.jesusvilla.movies.utils.toFilterList
import com.jesusvilla.movies.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment: BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by hiltNavGraphViewModels(R.id.movies_graph)
    override fun getBaseViewModel() = viewModel

    private val adapter by lazy {
        setupAdapter()
    }

    private var defaultSelected = 1

    override fun setupUI() {
        super.setupUI()
        with(binding) {
            list.addItemDecoration(getInsetDividerItemDecoration())
            list.adapter = adapter
        }
        if(isNetworkAvailable()) {
            viewModel.getProfile()
        } else {
            viewModel.getLocalProfile()
        }
        resetOption()
    }

    override fun setupListeners() {
        super.setupListeners()
        with(binding) {
            btnAll.setOnClickListener {
                selectOption(1)
                resetOption()
            }
            btnRecomendados.setOnClickListener {
                selectOption(2)
                resetOption()
            }
            btnCalificados.setOnClickListener {
                selectOption(3)
                resetOption()
            }
        }
    }

    override fun setUpObservers() {
        super.setUpObservers()
        viewModel.getBaseUIList().observe(viewLifecycleOwner) { setItems(it) }
    }

    private fun setupAdapter() = MoviesAdapter(requireContext(), listOf())

    private fun setItems(list: List<BaseUI>) {
        adapter.updateData(list)
    }

    private fun selectOption(selected: Int) {
        defaultSelected = selected
        viewModel.getBaseUIList().value?.let {
            if(it.isNotEmpty()) {
                setItems(it.toFilterList(selectOption = defaultSelected))
            } else {
                setItems(emptyList())
            }
        }
    }

    private fun resetOption() {
       /* with(binding) {
            when(defaultSelected) {
                1 -> {
                    btnAll.isSelected = true
                    btnCalificados.isSelected = false
                    btnCalificados.isSelected = false
                }
                2 -> {
                    btnAll.isChecked = false
                    btnRecomendados.isChecked = true
                    btnCalificados.isChecked = false
                }
                3 -> {
                    btnAll.isChecked = true
                    btnRecomendados.isChecked = false
                    btnCalificados.isChecked = true
                }
            }
        }*/
    }
}