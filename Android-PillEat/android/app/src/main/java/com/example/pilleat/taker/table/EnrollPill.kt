package com.example.pilleat.taker.table

data class EnrollPill(
    var userId: Int,
    var name: String,
    var category: String,
    var date: Int,
    var time: ArrayList<Times>,
    var day: String,
    var iot: Boolean
)

data class Times(
    var time1: String,
    var time2: String,
    var time3: String
)
