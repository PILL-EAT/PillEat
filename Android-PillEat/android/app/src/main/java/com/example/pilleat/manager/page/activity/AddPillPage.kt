package com.example.pilleat.manager.page.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.databinding.ActivityAddpillBinding
import com.example.pilleat.manager.response.AddPillResponse
import com.example.pilleat.manager.service.AddPillService
import com.example.pilleat.manager.table.AddPill
import com.example.pilleat.manager.view.AddPillView
import retrofit2.Response

class AddPillPage: AppCompatActivity(), AddPillView {
    private lateinit var binding: ActivityAddpillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddpillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addPillSaveBtn.setOnClickListener {
            Toast.makeText(this@AddPillPage, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addPill() {
        if(binding.addPillNameEt.text.toString().isEmpty() || binding.addPillUseEt.text.toString().isEmpty()) {
            Toast.makeText(this@AddPillPage, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()

            val pill_name : String = binding.addPillNameEt.text.toString()
            val pill_use : String = binding.addPillUseEt.text.toString()

            val addPillService = AddPillService()
            addPillService.setAddPillView(this@AddPillPage)

            addPillService.addPill(AddPill(pill_name, pill_use))
        }
    }

    override fun onAddPillSuccess() {
        Toast.makeText(this@AddPillPage, "약이 추가되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onAddPillFailure(response: Response<AddPillResponse>) {
        Toast.makeText(this@AddPillPage, response.message(), Toast.LENGTH_SHORT).show()
    }
}