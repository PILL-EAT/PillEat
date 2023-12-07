package com.example.pilleat.taker.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pilleat.databinding.FragmentEnrolllistBinding
import com.example.pilleat.taker.table.EnrollPill

class EnrollPillListFragment: Fragment() {
    private lateinit var binding: FragmentEnrolllistBinding
    private var drugDatas: ArrayList<EnrollPill> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnrolllistBinding.inflate(inflater, container, false)

        //drugDatas =
        //val enrollRVAdapter = EnrollRVAdapter()
        return binding.root
    }

    private fun getDrugs() {
    }
}