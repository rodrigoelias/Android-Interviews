package com.rodrigoelias.testwise.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.data.v2.pokedex


// Abstracts the Model from the ViewModel.
// The Repository is responsible for the abstraction between the API EndPoint and the data to be show
// by the ViewModel
class PokemonRepository(private val remoteSource: RemoteDataSource = RemoteDataSource()) : RepositoryListener {
    private val pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val dataRequestStatus: MutableLiveData<Status> = MutableLiveData()

    //private val localSource :  = RemoteDataSource()

    override fun onFail() {
        dataRequestStatus.postValue(Status.FAILED)
    }

    override fun onSuccess(apiResponse: pokedex) {
        val pokemons = mapAndFilter(apiResponse)

        dataRequestStatus.postValue(Status.SUCCESS)
        pokemonList.postValue(pokemons)
    }

    fun getEmAll() : LiveData<List<Pokemon>> {
        dataRequestStatus.postValue(Status.STARTED)
        remoteSource.fetchFromRemote(this)
        return pokemonList
    }

    fun getStatus() : LiveData<Status>{
        return dataRequestStatus
    }

    enum class Status { STARTED, FAILED, SUCCESS, NONE }

    private fun mapAndFilter(data: pokedex): List<Pokemon> {
        return data.pokemon_entries
                .map { Pokemon(it.entry_number, it.pokemon_species.name )}
                .sortedBy { it.Id }
    }
}

// RepositoryListener is the callback to be executed by the PokemonRepository after the request
// is completed
interface RepositoryListener {
    fun onSuccess(apiResponse: pokedex)
    fun onFail()
}