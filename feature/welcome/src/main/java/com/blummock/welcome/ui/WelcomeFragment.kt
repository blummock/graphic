package com.blummock.welcome.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.blummock.base.BaseFragment
import com.blummock.welcome.databinding.FragmentWelcomeBinding
import com.blummock.welcome.vm.WelcomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {

    override val viewModel by viewModels<WelcomeViewModel>()

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding
        get() = FragmentWelcomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        withBinding {
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