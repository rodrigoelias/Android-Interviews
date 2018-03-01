package com.rodrigoelias.testwise.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


// Abstracts the Model from the ViewModel.
// The Repository is responsible for the abstraction between the API EndPoint and the data to be show
// by the ViewModel
class PokemonRepository(private val remoteSource: RemoteDataSource = RemoteDataSource()) : RepositoryListener {
    private val pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val dataRequestStatus: MutableLiveData<Status> = MutableLiveData()

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
    //private val localSource :  = RemoteDataSource()

    fun getEmAll() : LiveData<List<Pokemon>> {
        dataRequestStatus.postValue(Status.STARTED)
        remoteSource.fetchFromRemote(this)
        return pokemonList
    }

    fun getStatus() : LiveData<Status>{
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

// RepositoryListener is the callback to be executed by the PokemonRepository after the request
// is completed
interface RepositoryListener {
    fun onSuccess(data: PokeAPIResponse)
    fun onFail()
}