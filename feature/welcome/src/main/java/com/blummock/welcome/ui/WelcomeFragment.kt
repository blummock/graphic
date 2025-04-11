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
import com.blummock.core.Destinations
import com.blummock.core.Router
import com.blummock.core.RouterArgs
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
        withBinding {
            startButton.setOnClickListener {
                (requireActivity() as Router).navigate(
                    Destinations.GraphicDestination.buildRoute(
                        RouterArgs.GraphicArgs(
                            countsInput.text.toString().toInt(),
                        )
                    )
                )
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