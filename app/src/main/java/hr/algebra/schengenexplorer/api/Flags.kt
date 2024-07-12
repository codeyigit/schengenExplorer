package hr.algebra.schengenexplorer.api

import com.google.gson.annotations.SerializedName


data class Flags (

    @SerializedName("png") val png : String,
    @SerializedName("svg") val svg : String,
    @SerializedName("alt") val alt : String
)
