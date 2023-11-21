package com.example.emiencecodetest

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: ArrayList<Data>,
)

data class Data(

    val _id: Int?=0,

    @SerializedName("sportId") var sportId:String,
    @SerializedName("openDate") var openDate: String,

)
