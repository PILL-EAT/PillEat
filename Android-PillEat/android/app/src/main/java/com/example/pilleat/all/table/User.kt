package com.example.pilleat.all.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    val email: String,
    val password: String,
    val name: String,
    val birth: String,
    val phone: String,
    val mode: String,
    val join_date: String
){
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}
