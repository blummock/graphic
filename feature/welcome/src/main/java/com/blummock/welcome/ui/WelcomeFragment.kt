package com.blummock.welcome.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.blummock.base.BaseFragment
import com.blummock.welcome.databinding.FragmentWelcomeBinding
import com.blummock.welcome.vm.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
internal class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {

    override val viewModel by viewModels<WelcomeViewModel>()

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding
        get() = FragmentWelcomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            toolbar.setNavigationOnClickListener {
                exitProcess(0)
            }
            startButton.setOnClickListener {
                viewModel.goToGraphic()
            }
            countsInput.doOnTextChanged { text, _, _, _ ->
                viewModel.setCount(text.toString())
            }
        }

        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    withBinding {
                        startButton.isEnabled = it.pointsCount.isNotBlank()
                    }
                }
        }
    }
}