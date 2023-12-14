package com.example.pilleat.taker.page.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.DialogEnrollTakerBinding
import com.example.pilleat.protector.service.SetTakerService
import com.example.pilleat.taker.page.activity.MainTakerPage

class SetTakerDialog(private val activity: AppCompatActivity): Dialog(activity) {
    private lateinit var binding: DialogEnrollTakerBinding
    private var listener: SetTakerDialogListener? = null

    fun setListener(listener: MainTakerPage) {
        this.listener = listener
    }

    interface SetTakerDialogListener {
        fun onPhoneSet(phone: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEnrollTakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        // 빈 화면 터치 시, dialog 사라지도록 하기
        setCancelable(true)
    }
}