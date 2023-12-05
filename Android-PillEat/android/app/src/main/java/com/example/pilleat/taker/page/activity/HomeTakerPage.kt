package com.example.pilleat.taker.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityHometakerBinding
import com.example.pilleat.taker.page.fragment.EnrollFragment
import com.example.pilleat.taker.page.fragment.SearchFragment
import com.example.pilleat.taker.page.fragment.TakingYnFragment

class HomeTakerPage: AppCompatActivity() {
    private lateinit var binding: ActivityHometakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHometakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, SearchFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.takingYnFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, TakingYnFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.enrollFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, EnrollFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}