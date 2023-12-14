package com.example.pilleat.protector.service

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pilleat.getRetrofit
import com.example.pilleat.protector.response.SetTakerResponse
import com.example.pilleat.protector.view.SetTakerView
import com.example.pilleat.taker.retrofit_interface.TakerRetrofitInterface
import com.example.pilleat.taker.table.PhoneNumber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetTakerService {
    private lateinit var setTakerView: SetTakerView

    fun initSetTakerView(setTakerView: SetTakerView) {
        this.setTakerView = setTakerView
    }

    fun inputTaker(phone: PhoneNumber, takerId: Int) {
        val setTakerService = getRetrofit().create(TakerRetrofitInterface::class.java)
        setTakerService.setTaker(phone, takerId).enqueue(object: Callback<SetTakerResponse> {
            override fun onResponse(call: Call<SetTakerResponse>, response: Response<SetTakerResponse>) {
                Log.d("SET-PROTECTOR", response.toString())

//                val resp: SetTakerResponse = response.body()!!
//                when(resp.code) {
//                    200 -> setTakerView.onSetTakerSuccess(resp.code)
//                    else -> setTakerView.onSetTakerFailure(resp.code, resp.message)
//                }
                val resp: SetTakerResponse? = response.body()
                if (response.isSuccessful && resp != null) {
                    when(resp.code) {
                        200 -> setTakerView.onSetTakerSuccess(resp.code)
                        else -> setTakerView.onSetTakerFailure(resp.code, resp.message)
                    }
                } else {
                    Log.d("SET-PROTECTOR", "Response not successful or body is null")
                    // Handle the case where the response is not successful or the body is null
                    setTakerView.onSetTakerFailure(response.code(), "Response not successful or body is null")
                }
            }

            override fun onFailure(call: Call<SetTakerResponse>, t: Throwable) {
                Log.d("SET-TAKER-FAILURE", t.toString())

                setTakerView.onSetTakerFailure(-1, "Failed to make the API call")
            }
        })
    }
}