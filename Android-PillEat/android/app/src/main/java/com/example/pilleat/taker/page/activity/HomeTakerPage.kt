package com.example.pilleat.taker.page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.R
import com.example.pilleat.all.page.activity.UpdatePage
import com.example.pilleat.databinding.ActivityHometakerBinding
import com.example.pilleat.taker.page.fragment.EnrollPillListFragment
import com.example.pilleat.taker.page.fragment.TakingYnFragment

class HomeTakerPage: AppCompatActivity() {
    private lateinit var binding: ActivityHometakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHometakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@HomeTakerPage, MainTakerPage::class.java)
        intent.putExtra("homeToMain", getData())
        startActivity(intent)
    }

    private fun getTakerType(): String {
        val getIntent = intent
        val getData = getIntent.getStringExtra("takerType")!!
        return getData
    }

    private fun getData(): Int {
        val getIntent = intent
        val getData = getIntent.getIntExtra("userId", 0)
        Log.d("HOME", getData.toString())
        return getData
    }

    private fun getData3(): Int {
        val getIntent = intent
        val getData3 = getIntent.getIntExtra("clientId", 0)
        return getData3
    }

    private fun sendData() {
        val intent = Intent(this@HomeTakerPage, UpdatePage::class.java)
        intent.putExtra("userId", getData())
        startActivity(intent)
    }

    private fun initBottomNavigation() {
        val bundle = Bundle().apply {
            putInt("userId", getData())
            putInt("clientId", getData3())
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