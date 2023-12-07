package com.example.pilleat.manager.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AddPill")
data class AddPill(
    @SerializedName("name") val pill_name: String,
    @SerializedName("use") val pill_use: String
) {
    @PrimaryKey(autoGenerate = true) val pill_id: Int = 0
}