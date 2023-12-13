package com.example.pilleat.protector.view

interface SetTakerView {
    fun onSetTakerSuccess(code: Int)
    fun onSetTakerFailure(code: Int, message: String)
}