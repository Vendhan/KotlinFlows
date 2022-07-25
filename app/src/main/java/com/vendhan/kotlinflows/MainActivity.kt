package com.vendhan.kotlinflows

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.vendhan.kotlinflows.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.livedataButton.setOnClickListener {
            mainViewModel.triggerLiveData()
        }
        binding.flowButton.setOnClickListener {
            // Needs to run on scope
            lifecycleScope.launchWhenStarted {
                mainViewModel.triggerFlow().collect {
                    Log.d(TAG, "Flow 1: $it")
                    binding.flowText.text = it
                }
            }
        }

        binding.stateFlowButton.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }

        binding.sharedFlowButton.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }

        subscribeToObservables()
    }

    private fun subscribeToObservables() {

        // Observing Livedata
        mainViewModel.liveData.observe(
            this
        ) {
            Log.d(TAG, "Livedata: $it")
            binding.livedataText.text = it
            // showSnackBar()
        }

        // Observing StateFlow
        lifecycleScope.launchWhenStarted {
            mainViewModel.stateFlow.collect {
                Log.d(TAG, "Stateflow: $it")
                binding.stateFlowText.text = it
                // showSnackBar()
            }
        }

        // Observing SharedFlow
        lifecycleScope.launchWhenStarted {
            mainViewModel.sharedFlow.collect {
                Log.d(TAG, "Sharedflow 1: $it")
                binding.sharedFlowText.text = it
                // showSnackBar()
            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            this,
            binding.root,
            "Hello World!!",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
