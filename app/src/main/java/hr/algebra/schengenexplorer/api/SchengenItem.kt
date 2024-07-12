package hr.algebra.schengenexplorer.api

import com.google.gson.annotations.SerializedName

data class SchengenItem(

    @SerializedName("flags") val flags : Flags,
    @SerializedName("name") val name : Name
)