package com.rodrigoelias.testwise.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.repository.Repository
import com.rodrigoelias.testwise.repository.PokemonRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class PokemonListViewModelTest {
    lateinit var viewModel: PokemonListViewModel
    lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = Mockito.mock(Repository::class.java)
    }

    @Test
    fun getStatus() {
        var data : LiveData<PokemonRepository.Status>  = MutableLiveData()
        Mockito.`when`(repository.getStatus()).thenReturn(data)

        viewModel = PokemonListViewModel(repository)
        assert(viewModel.status == data)
    }

    @Test
    fun getList() {
        var data : LiveData<List<Pokemon>>  = MutableLiveData()
        Mockito.`when`(repository.getEmAll()).thenReturn(data)

        viewModel = PokemonListViewModel(repository)
        assert(viewModel.list == data)
    }

}