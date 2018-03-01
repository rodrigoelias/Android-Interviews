package com.rodrigoelias.testwise.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Fetches the data from the remote endpoint and invokes the correct callback (fail/success)
class RemoteDataSource {
    companion object {
        const val TAG = "RemoteDataSource"
    }
    private var service: PokemonService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co//api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun fetchFromRemote(listener: RepositoryListener) {
        service.getThemAll().enqueue(object : Callback<PokeAPIResponse> {
            override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable?) {
                Log.d(TAG, "onFailure", t)
                listener.onFail()
            }

            override fun onResponse(call: Call<PokeAPIResponse>, response: Response<PokeAPIResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if(body == null){
                        return
                    }

                    listener.onSuccess(body)
                } else {
                    System.out.println(response.errorBody())
                }
            }
        })
    }

    private interface PokemonService {
        @GET("v1/pokedex/1")
        fun getThemAll(): Call<PokeAPIResponse>
    }
}
