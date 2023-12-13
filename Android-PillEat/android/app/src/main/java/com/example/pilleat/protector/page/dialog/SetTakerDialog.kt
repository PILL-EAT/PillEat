package com.example.pilleat.protector.page.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.DialogEnrollTakerBinding

class SetTakerDialog(private val activity: AppCompatActivity): Dialog(activity) {
    private lateinit var binding: DialogEnrollTakerBinding
    private var listener: SetTakerDialogListener? = null

    fun setListener(listener: SetTakerDialogListener) {
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

        binding.dialogEnrollBtn.setOnClickListener {
            val phone = binding.dialogEnrollTakerEt.text.toString()
            listener?.onPhoneSet(phone)
            dismiss()
        }

    }

    private fun initViews() {
        // 빈 화면 터치 시, dialog 사라지도록 하기
        setCancelable(true)
    }
}