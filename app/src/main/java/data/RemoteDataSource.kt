package data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RemoteDataSource {
    private var service: PokemonService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co//api/v1/pokedex/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun fetchFromRemote(listener: RepositoryListener) {
        service.getThemAll().enqueue(object : Callback<PokeAPIResponse> {
            override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable?) {
                t?.printStackTrace()
                listener.onFail()
            }

            override fun onResponse(call: Call<PokeAPIResponse>, response: Response<PokeAPIResponse>) {
                if (response.isSuccessful) {
                    var s = response.body()

                    s?.let {
                        listener.onSuccess(it.pokemon.map { it.mapToPokemon() }
                                .filter { it.Id < 1000 }
                                .sortedBy { it.Id })
                    }
                } else {
                    System.out.println(response.errorBody());
                }
            }
        })
    }

    private interface PokemonService {
        @GET("1/")
        fun getThemAll(): Call<PokeAPIResponse>
    }

    private fun RemoteNode.mapToPokemon() = Pokemon(mapFromResourceUriToId(resourceUri), name)

    private fun mapFromResourceUriToId(resourceUri: String): Int {
        val match = resourceUri.split('/')

        return when {
            match.size < 2 -> match[match.size - 2].toInt()
            else -> 0
        }
    }

}
