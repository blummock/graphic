package com.blummock.randgraphic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import com.blummock.randgraphic.databinding.ActivityMainBinding
import com.blummock.router.Router
import com.blummock.router.Routes

class MainActivity : Router, AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
    }

    override fun navigate(routes: Routes) {
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