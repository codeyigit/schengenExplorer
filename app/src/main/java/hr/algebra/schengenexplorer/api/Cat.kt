package hr.algebra.schengenexplorer.api

import com.google.gson.annotations.SerializedName

data class Cat (

    @SerializedName("official") val official : String,
    @SerializedName("common") val common : String
)
