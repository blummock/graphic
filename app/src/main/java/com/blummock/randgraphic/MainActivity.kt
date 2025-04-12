package com.blummock.randgraphic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import com.blummock.domain.router.Router
import com.blummock.domain.router.Routes
import com.blummock.randgraphic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeRoutes()
    }

    private fun observeRoutes() {
        lifecycleScope.launch {
            router.observe()
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    navigate(it)
                }
        }
    }

    private fun navigate(routes: Routes) {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
                ?: return
        val navController = navHost.navController
        when (routes) {
            is Routes.Link -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(routes.uri.toUri())
                    .build()
                navController.navigate(request)
            }

            is Routes.Back -> {
                navController.popBackStack()
            }
        }
    }
}