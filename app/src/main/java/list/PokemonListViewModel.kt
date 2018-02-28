package list

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
        //TODO : Remove after UnitTest
        pokemonList.postValue(listOf(Pokemon("Bulbasaur"), Pokemon("Ivysaur")))
    }

    data class Pokemon(val name: String)
}