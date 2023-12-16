package com.example.pilleat.protector.page.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityHomeprotectorBinding
import com.example.pilleat.taker.page.fragment.EnrollPillListFragment
import com.example.pilleat.taker.page.fragment.TakingYnFragment

class HomeProtectorPage: AppCompatActivity() {
    private lateinit var binding: ActivityHomeprotectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeprotectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("protectorId", 0)
        return getData
    }

    private fun getTakerId() : Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("getTakerId", 0)
        return getData
    }

    private fun initBottomNavigation() {
        val bundle = Bundle().apply {
            putInt("userId", getTakerId())
            putInt("userIdx", getTakerId())
            putInt("protectorId", getData())
            Log.d("homehomehome", getTakerId().toString())
        }

        val takingYnFragment = TakingYnFragment()
        takingYnFragment.arguments = bundle

        val enrollPillListFragment = EnrollPillListFragment()
        enrollPillListFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, TakingYnFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.takingYnFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, TakingYnFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.enrollFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, EnrollPillListFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}