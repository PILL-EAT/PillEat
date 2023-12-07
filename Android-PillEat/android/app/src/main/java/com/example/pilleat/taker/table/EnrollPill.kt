package com.example.pilleat.taker.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EnrollPill")
data class EnrollPill(
    var pill_name: String? = "",
    var pill_category: String? = "",
    var pill_time: String? = "",
    var pill_volumn: String? = ""
){
    @PrimaryKey(autoGenerate = true) var pill_id: Int = 0
}
