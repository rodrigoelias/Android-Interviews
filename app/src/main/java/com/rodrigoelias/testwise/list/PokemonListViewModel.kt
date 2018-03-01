package com.rodrigoelias.testwise.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.data.PokemonRepository
import com.rodrigoelias.testwise.data.RepositoryListener

class PokemonListViewModel(repository: PokemonRepository = PokemonRepository()) : RepositoryListener, ViewModel() {
    private val pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val repositoryRequestStatus: MutableLiveData<Status> = MutableLiveData()

    val status: LiveData<Status>
        get() = repositoryRequestStatus

    val list: LiveData<List<Pokemon>>
        get() = pokemonList

    init {
        repositoryRequestStatus.postValue(Status.STARTED)
        repository.getEmAll(this)
    }

    override fun onFail() {
        repositoryRequestStatus.postValue(Status.FAILED)
    }

    override fun onSuccess(data: List<Pokemon>) {
        handleResponseFromRepository(data.map { it })
    }

    private fun handleResponseFromRepository(newValues: List<Pokemon>) {
        pokemonList.postValue(newValues)
        repositoryRequestStatus.postValue(Status.SUCCESS)
    }

    enum class Status { STARTED, FAILED, SUCCESS, NONE }
}