package com.rodrigoelias.testwise

import android.arch.lifecycle.MutableLiveData
import com.rodrigoelias.testwise.data.Pokemon
import com.rodrigoelias.testwise.list.PokemonListViewModel
import com.rodrigoelias.testwise.repository.PokemonRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class PokeViewModelTest {
    lateinit var viewModel: PokemonListViewModel
    lateinit var repository: PokemonRepository

    @Before
    fun setup(){
        repository = mock(PokemonRepository::class.java)
        viewModel = PokemonListViewModel(repository)
    }

    @Test
    fun addition_isCorrect() {
//        var data = MutableLiveData<List<Pokemon>>()
//        Mockito.`when`(repository.getEmAll()).thenReturn(data)
//
//        verify(repository, atLeastOnce()).onSuccess(ArgumentMatchers.any()
//
//        verify(viewModel, never()).onFail()
//        verify(viewModel, atLeastOnce()).onSuccess(ArgumentMatchers.any())
    }
}
