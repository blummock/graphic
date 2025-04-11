package com.blummock.graphic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.blummock.base.BaseFragment
import com.blummock.core.Destinations
import com.blummock.graphic.databinding.FragmentGraphicBinding
import com.blummock.graphic.vm.GraphicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class GraphicFragment : BaseFragment<FragmentGraphicBinding, GraphicViewModel>() {

    override val viewModel by viewModels<GraphicViewModel>()

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGraphicBinding
        get() = FragmentGraphicBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val points = Destinations.GraphicDestination.getArgs(requireArguments()).pointsCount
        viewModel.init(points)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    withBinding {
                        textView.text = it.points.toString()
                    }
                }
        }
    }
}