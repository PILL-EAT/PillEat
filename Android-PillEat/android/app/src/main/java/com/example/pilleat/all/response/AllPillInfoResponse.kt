package com.example.pilleat.all.response

import com.google.gson.annotations.SerializedName

data class AllPillInfoResponse(
    @SerializedName("response") val response: Response
)

data class Response(
    @SerializedName("header") val header: Header,
    @SerializedName("body") val body: Body
)

data class Header(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("resultMsg") val resultMsg: String
)

data class Body(
    @SerializedName("pageNo") val pageNo: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("numOfRows") val numOfRows: Int,
    @SerializedName("items") val items: ArrayList<ItemResult>
)

data class ItemResult(
    @SerializedName("item") val item: Item
)

data class Item(
    @SerializedName("ESSNTL_ITEM_NAME") val ESSNTL_ITEM_NAME: String,
    @SerializedName("MED_EFFICACY") val MED_EFFICACY: String,
    @SerializedName("APPOINT_DATE") val APPOINT_DATE: String
)
