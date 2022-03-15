package com.meokg456.note.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.meokg456.note.R
import com.meokg456.note.databinding.ActivityCountDemoBinding
import com.meokg456.note.viewmodel.CountViewModel
import com.meokg456.note.viewmodel.CountViewModelFactory

class CountDemo : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCountDemoBinding
    private lateinit var countViewModel: CountViewModel
    private lateinit var countViewModelFactory: CountViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_count_demo)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            countViewModel.increase()
        }

        countViewModelFactory = CountViewModelFactory(10)
        countViewModel = ViewModelProvider(this, countViewModelFactory)[CountViewModel::class.java]
        binding.viewModel = countViewModel
        val countObserver = Observer<Int> {
            binding.fab.text = it.toString()
        }

        countViewModel.count.observe(this, countObserver)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_count_demo)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}