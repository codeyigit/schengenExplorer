package hr.algebra.schengenexplorer.api

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import hr.algebra.schengenexplorer.Model.Item
import hr.algebra.schengenexplorer.SCHENGEN_PROVIDER_CONTENT_URI
import hr.algebra.schengenexplorer.SchengenReceiver
import hr.algebra.schengenexplorer.framework.sendBroadcast
import hr.algebra.schengenexplorer.handler.downloadAndStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SchengenFetcher(private val context: Context) {

    private val schengenApi: SchengenApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        schengenApi=retrofit.create(SchengenApi::class.java)
    }


    fun fetchItems(count : Int){
        val request = schengenApi.fethItems(53)

        request.enqueue(object: Callback<List<SchengenItem>> {
            override fun onResponse(
                call: Call<List<SchengenItem>>,
                response: Response<List<SchengenItem>>
            ) {
                response.body()?.let {
                    populateItems(it)
                }
            }

            override fun onFailure(call: Call<List<SchengenItem>>, t: Throwable) {
                Log.e(javaClass.name,t.toString())
            }

        })

    }

    private fun populateItems(schengenItems: List<SchengenItem>) {

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {


            schengenItems.forEach {
                val picturePath = downloadAndStore(context,it.flags.png)

                val values=ContentValues().apply {
                    put(Item::commonName.name,it.name.common)
                    put(Item::officialName.name,it.name.official)
                    put(Item::flagDescription.name,it.flags.alt)
                    put(Item::picturePath.name,picturePath?:"")
                    put(Item::read.name,false)
                }
                context.contentResolver.insert(SCHENGEN_PROVIDER_CONTENT_URI,values)

            }
        }

        context.sendBroadcast<SchengenReceiver>()
    }
}