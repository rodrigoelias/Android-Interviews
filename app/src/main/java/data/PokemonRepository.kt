package data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by rodrigoelias on 28/02/2018.
 */

class PokemonRepository  {

    private var service: PokemonService

    init{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co//api/v1/pokedex/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun fetchFromRemote(listener: RepositoryListener) {
        service.getThemAll().enqueue(object: Callback<PokeAPIResponse>{
            override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable?) {
                t?.printStackTrace()
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<PokeAPIResponse>, response: Response<PokeAPIResponse>) {
                if (response.isSuccessful) {
                    var s = response.body()

                    s?.let{listener.OnSuccess(it.pokemon)}
                } else {
                    System.out.println(response.errorBody());
                }
            }
        })
    }
}

private interface PokemonService{
    @GET("1/")
    fun getThemAll(): Call<PokeAPIResponse>
}

interface RepositoryListener{
    fun OnSuccess( data : List<Pokemon>)
    fun OnFail()
}