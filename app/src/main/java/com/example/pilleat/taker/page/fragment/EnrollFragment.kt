package com.example.pilleat.taker.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pilleat.databinding.FragmentEnrolllistBinding

class EnrollFragment: Fragment() {
    private lateinit var binding: FragmentEnrolllistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnrolllistBinding.inflate(inflater, container, false)

        return binding.root
    }
}