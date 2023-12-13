package com.example.pilleat.all.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Auth")
data class Auth(
    @PrimaryKey(autoGenerate = false) val email: String,
    val password: String
)
