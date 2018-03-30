package com.github.akvast.transitiontest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.akvast.mvvm.utils.contentView
import com.github.akvast.transitiontest.R
import com.github.akvast.transitiontest.databinding.ActivityMainBinding
import com.github.akvast.transitiontest.ui.adapter.MainAdapter


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.reload(binding.swipeRefreshLayout)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.reload()
    }

}