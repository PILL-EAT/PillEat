package com.example.pilleat.manager.view

import com.example.pilleat.manager.response.AddPillResponse
import retrofit2.Response

interface AddPillView {
    fun onAddPillSuccess()
    fun onAddPillFailure(response: Response<AddPillResponse>)
}