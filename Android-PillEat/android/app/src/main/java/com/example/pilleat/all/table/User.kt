package com.example.pilleat.all.table

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class User(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("date") val date: String,
    @SerializedName("mode") val mode: String
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}
