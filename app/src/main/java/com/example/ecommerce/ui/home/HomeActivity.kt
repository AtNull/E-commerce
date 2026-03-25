package com.example.ecommerce.ui.home

import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setupActivityTransition()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat
            .getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupViewPager()
        setupAppbarTransition()
        consumePadding()
    }

    private fun setupActivityTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        window.sharedElementsUseOverlay = false
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = HomeViewPagerAdapter(this)

        // Fast scrolling easily conflicts with swipe gesture, so I had to disable it.
        // There are some solutions but they are fiddly at best
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Products"
                1 -> "Favorites"
                else -> null
            }
        }.attach()
    }

    private fun setupAppbarTransition() {
        binding.appBar.addOnOffsetChangedListener { layout, offset ->
            val percentage = abs(offset).toFloat() / layout.totalScrollRange

            binding.toolbar.alpha = 1.0f - percentage
        }
    }

    private fun consumePadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
    }

}
