package com.rodrigoelias.score.data

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
    private var service: CreditReportInfoService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://5lfoiyb0b3.execute-api.us-west-2.amazonaws.com/prod/mockcredit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(CreditReportInfoService::class.java)
    }

    fun fetchFromRemote(listener: RepositoryListener) {
        service.get().enqueue(object : Callback<APIResponse> {
            override fun onFailure(call: Call<APIResponse>?, t: Throwable?) {
                Log.d(TAG, "onFailure", t)
                listener.onFail()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if(body == null){
                        return
                    }

                    listener.onSuccess(body.creditReport)
                } else {
                    System.out.println(response.errorBody())
                }
            }
        })
    }

    private interface CreditReportInfoService {
        @GET("values/")
        fun get(): Call<APIResponse>
    }
}
