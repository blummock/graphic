package com.blummock.graphic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blummock.base.BaseFragment
import com.blummock.graphic.databinding.FragmentGraphicBinding
import com.blummock.graphic.recycler.GridDividerItemDecoration
import com.blummock.graphic.recycler.PointsAdapter
import com.blummock.graphic.vm.GraphicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class GraphicFragment : BaseFragment<FragmentGraphicBinding, GraphicViewModel>() {

    override val viewModel by viewModels<GraphicViewModel>()

    private val adapter by lazy { PointsAdapter() }

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGraphicBinding
        get() = FragmentGraphicBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        viewModel.init(requireArguments())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = adapter
            val dividerColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
            recyclerView.addItemDecoration(GridDividerItemDecoration(2, dividerColor))
        }
        withBinding {
            toolbar.setNavigationOnClickListener {
                viewModel.navigateBack()
            }
        }
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle)
                .map { it.points }
                .distinctUntilChanged()
                .collectLatest { points ->
                    withBinding {
                        graphicSurface.setPoints(points)
                        adapter.submitList(points)
                    }
                }
        }
    }
}