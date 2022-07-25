package com.vendhan.kotlinflows

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vendhan.kotlinflows.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flowButton.setOnClickListener {
            // Flow 2
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.triggerFlow()
                        .collect {
                            println("Collected from Flow 2: $it")
                        }
                }
            }
        }

        binding.sharedFlowButton.setOnClickListener {
            // Shared Flow 2
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.sharedFlow
                        .collect {
                            println("Collected from Shared Flow 2: $it")
                        }
                }
            }
        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {

        // Flow 1
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.triggerFlow()
                    .collect {
                        println("Collected from Flow 1: $it")
                    }
            }
        }

        // Shared Flow 1
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.sharedFlow
                    .collect {
                        println("Collected from Shared Flow 1: $it")
                    }
            }
        }
    }
}
