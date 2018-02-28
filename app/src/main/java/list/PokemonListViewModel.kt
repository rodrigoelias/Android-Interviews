package list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import data.PokemonRepository
import data.RepositoryListener

/**
 * Created by rodrigoelias on 28/02/2018.
 */
class PokemonListViewModel(repository: PokemonRepository = PokemonRepository()) : RepositoryListener, ViewModel(){
    private val pokemonList : MutableLiveData<List<Pokemon>> = MutableLiveData()

    val list : LiveData<List<Pokemon>>
        get() =  pokemonList

    init {
        repository.fetchFromRemote(this)
    }

    override fun OnFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccess(data: List<data.Pokemon>) {
        handleResponseFromRepository(data.map { Pokemon(it.name) })
    }

    private fun handleResponseFromRepository(newValues: List<Pokemon> ){
        pokemonList.postValue(newValues)
    }

    data class Pokemon(val name: String)
}