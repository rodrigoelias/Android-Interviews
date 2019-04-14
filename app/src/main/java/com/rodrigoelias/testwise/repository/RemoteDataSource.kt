package com.rodrigoelias.testwise.repository

import android.util.Log
import com.rodrigoelias.testwise.data.v2.pokedex
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RemoteDataSource {
    companion object {
        const val TAG = "RemoteDataSource"
    }
    private var service: PokemonService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun fetchFromRemote(listener: RepositoryListener) {
        service.getThemAll().enqueue(object : Callback<pokedex> {
            override fun onFailure(call: Call<pokedex>?, t: Throwable?) {
                Log.d(TAG, "onFailure", t)
                listener.onFail()
            }

            override fun onResponse(call: Call<pokedex>, response: Response<pokedex>) {
                if (response.isSuccessful) {
                    val body = response.body() ?: return

                    listener.onSuccess(body)
                } else {
                    listener.onFail()
                }
            }
        })
    }

    private interface PokemonService {
        @GET("v2/pokedex/2")
        fun getThemAll(): Call<pokedex>
    }
}
