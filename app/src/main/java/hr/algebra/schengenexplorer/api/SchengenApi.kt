package hr.algebra.schengenexplorer.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL= "https://restcountries.com/v3.1/"


interface SchengenApi {

    @GET("region/europe?fields=name,flags")
    fun fethItems(@Query("count") count: Int = 53)
        : Call<List<SchengenItem>>
}