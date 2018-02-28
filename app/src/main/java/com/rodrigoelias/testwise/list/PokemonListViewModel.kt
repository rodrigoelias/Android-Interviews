package com.rodrigoelias.testwise.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by rodrigoelias on 28/02/2018.
 */
class PokemonListViewModel : ViewModel(){
    private val pokemonList : MutableLiveData<List<Pokemon>> = MutableLiveData()

    val list : LiveData<List<Pokemon>>
        get() =  pokemonList

    init {
        pokemonList.postValue(listOf(Pokemon("Bulbasaur", "1"), Pokemon("Ivysaur", "2")))
    }

}