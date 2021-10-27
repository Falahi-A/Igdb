package com.pinch.codeassignment.igdb.ui.main

import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.pinch.codeassignment.igdb.databinding.ActivityMainBinding
import com.pinch.codeassignment.igdb.ui.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    lateinit var navHostFragment: NavHostFragment
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = { layoutInflater ->
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {


         navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment


    }
}