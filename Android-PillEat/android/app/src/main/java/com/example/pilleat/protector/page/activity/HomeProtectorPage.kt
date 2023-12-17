package com.example.pilleat.protector.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityHomeprotectorBinding
import com.example.pilleat.taker.page.activity.MainTakerPage
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@HomeProtectorPage, MainProtectorPage::class.java)
        intent.putExtra("homeProtectorToMain", getData())
        startActivity(intent)
    }

    private fun getTakerType(): String {
        val getIntent = intent
        val getData = getIntent.getStringExtra("protectorType")!!
        return getData
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("protectorId", 0)
        Log.d("HOME-protectorId", getData.toString())
        return getData
    }

    private fun getTakerId() : Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("getTakerId", 0)
        Log.d("HOME-takerId", getData.toString())
        return getData
    }

    private fun initBottomNavigation() {
        val bundle = Bundle().apply {
            putInt("userId", getTakerId())
            putInt("userIdx", getTakerId())
            putString("takerType", getTakerType())
        }

        val takingYnFragment = TakingYnFragment()
        takingYnFragment.arguments = bundle

        val enrollPillListFragment = EnrollPillListFragment()
        enrollPillListFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, takingYnFragment)
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.takingYnFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, takingYnFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.enrollFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, enrollPillListFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}