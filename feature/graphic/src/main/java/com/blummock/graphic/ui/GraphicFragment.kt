package com.blummock.graphic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.blummock.base.BaseFragment
import com.blummock.graphic.databinding.FragmentGraphicBinding
import com.blummock.graphic.vm.GraphicViewModel
import com.blummock.router.Destinations

class GraphicFragment : BaseFragment<FragmentGraphicBinding, GraphicViewModel>() {

    override val viewModel by viewModels<GraphicViewModel>()

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGraphicBinding
        get() = FragmentGraphicBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            textView.text =
                Destinations.GraphicDestination.getArgs(requireArguments()).pointsCount.toString()
        }
    }
}