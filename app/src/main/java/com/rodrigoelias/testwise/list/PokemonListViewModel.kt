package com.rodrigoelias.testwise.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.data.PokemonRepository
import com.rodrigoelias.testwise.data.PokemonRepository.Status


class PokemonListViewModel(repository: PokemonRepository = PokemonRepository()) : ViewModel() {
    private val pokemonList: LiveData<List<Pokemon>> = repository.getEmAll()
    private val repositoryRequestStatus: LiveData<Status> = repository.getStatus()

    val status: LiveData<Status>
        get() = repositoryRequestStatus

    val list: LiveData<List<Pokemon>>
        get() = pokemonList

}
