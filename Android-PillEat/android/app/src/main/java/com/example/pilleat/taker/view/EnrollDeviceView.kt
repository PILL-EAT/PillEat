package com.example.pilleat.taker.view

interface EnrollDeviceView {
    fun onSetIotDeviceSuccess(code: Int)
    fun onSetIotDeviceFailure(code: Int, message: String)
}