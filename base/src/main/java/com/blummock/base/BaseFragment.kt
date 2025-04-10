package com.blummock.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewBinding, VM : BaseViewModel<*, *>> : Fragment() {

    abstract val viewModel: VM

    abstract val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> B

    private var binding: B? = null

    fun withBinding(block: B.() -> Unit) {
        block.invoke(binding!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val bind = bindingCallback(inflater, container, false)
        binding = bind
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.effect
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    when (it) {
                        is BaseEffect.ErrorEffect -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}