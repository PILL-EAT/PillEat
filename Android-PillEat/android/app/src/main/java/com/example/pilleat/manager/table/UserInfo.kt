package com.example.pilleat.manager.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
data class UserInfo(
    var pill_name: String? = "",
    var pill_category: String? = "",
    var pill_time: String? = "",
    var pill_volumn: String? = ""
){
    @PrimaryKey(autoGenerate = true) var pill_id: Int = 0
}
