package com.rodrigoelias.testwise.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rodrigoelias.testwise.data.PokeAPIResponse
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.data.RemoteNode

// Abstracts the Model from the ViewModel
// The Repository is responsible for the abstraction between the API EndPoint and the data to be show
// by the ViewModel
interface Repository {
    fun getEmAll(): LiveData<List<Pokemon>>
    fun getStatus(): LiveData<PokemonRepository.Status>
}

class PokemonRepository(private val remoteSource: RemoteDataSource = RemoteDataSource()) :
        NetworkCallHandler, Repository {

    private val pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val dataRequestStatus: MutableLiveData<Status> = MutableLiveData()

    //private val localSource :  = RemoteDataSource()

    override fun onFail() {
        dataRequestStatus.postValue(Status.FAILED)
    }

    override fun onSuccess(apiResponse: PokeAPIResponse) {
        // remove the Pokemons which have Id > 1000
        // those are duplicates and specials and we don't want them :(
        val pokemons = mapAndFilter(apiResponse)

        dataRequestStatus.postValue(Status.SUCCESS)
        pokemonList.postValue(pokemons)
    }

    override fun getEmAll(): LiveData<List<Pokemon>> {
        dataRequestStatus.postValue(Status.STARTED)
        remoteSource.fetchFromRemote(this)
        return pokemonList
    }

    override fun getStatus(): LiveData<Status> {
        return dataRequestStatus
    }

    enum class Status { STARTED, FAILED, SUCCESS, NONE }

    private fun mapAndFilter(data: PokeAPIResponse): List<Pokemon> {
        return data.pokemon.map { it.toPokemon() }
                .filter { it.Id < 1000 }
                .sortedBy { it.Id }
    }

    // Map RemoteNode entity to Pokemon (local entity)
    private fun RemoteNode.toPokemon() = Pokemon(mapFromResourceUriToId(resourceUri), name)

    //parses the resourceUri string and fetch the Id
    private fun mapFromResourceUriToId(resourceUri: String): Int {
        val match = resourceUri.split('/')

        return when {
            match.size > 2 -> match[match.size - 2].toInt()
            else -> 0
        }
    }
}

// NetworkCallHandler is the callback to be executed by the PokemonRepository after the request
// is completed
interface NetworkCallHandler {
    fun onSuccess(apiResponse: PokeAPIResponse)
    fun onFail()
}