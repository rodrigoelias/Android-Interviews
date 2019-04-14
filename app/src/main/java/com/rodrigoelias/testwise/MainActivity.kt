package com.rodrigoelias.testwise

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.rodrigoelias.testwise.repository.PokemonRepository
import kotlinx.android.synthetic.main.activity_main.rv_pokemon_list as recyclerView
import kotlinx.android.synthetic.main.activity_main.tv_error_message as errorMessageTextView
import kotlinx.android.synthetic.main.activity_main.progressBar
import com.rodrigoelias.testwise.list.PokeListAdapter
import com.rodrigoelias.testwise.list.PokemonListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonVm: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PokeListAdapter()

        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        subscribe(adapter)
    }

    private fun subscribe(myAdapter: PokeListAdapter) {
        pokemonVm = ViewModelProviders.of(this)
                .get(PokemonListViewModel::class.java)

        pokemonVm.list.observe(this, Observer { data ->
            myAdapter.dataSource = data!!
        })

        pokemonVm.status.observe(this, Observer { newStatus ->
            setState(newStatus)
        })
    }

    private fun setState(newStatus: PokemonRepository.Status?) {
        when (newStatus) {
            PokemonRepository.Status.STARTED -> {
                recyclerView.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            PokemonRepository.Status.FAILED -> {
                errorMessageTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            PokemonRepository.Status.SUCCESS -> {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            else -> {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE
            }
        }
    }
}
