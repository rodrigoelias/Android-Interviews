package com.rodrigoelias.testwise.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rodrigoelias.testwise.list.PokemonListViewModel


// Abstracts the Model from the ViewModel.
// The Repository is responsible for the abstraction between the API EndPoint and the data to be show
// by the ViewModel
class PokemonRepository(private val remoteSource: RemoteDataSource = RemoteDataSource()) : RepositoryListener {
    private val pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val dataRequestStatus: MutableLiveData<Status> = MutableLiveData()

    override fun onFail() {
        dataRequestStatus.postValue(Status.FAILED)
    }

    override fun onSuccess(data: List<Pokemon>) {
        dataRequestStatus.postValue(Status.SUCCESS)
        pokemonList.postValue(data)
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
}

// RepositoryListener is the callback to be executed by the PokemonRepository after the request
// is completed
interface RepositoryListener {
    fun onSuccess(data: List<Pokemon>)
    fun onFail()
}