package com.example.pilleat.taker.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityHometakerBinding

class HomeTakerPage: AppCompatActivity() {
    private lateinit var binding: ActivityHometakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHometakerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}