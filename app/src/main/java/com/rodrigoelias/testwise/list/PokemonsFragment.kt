package com.rodrigoelias.testwise.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigoelias.testwise.R
import com.rodrigoelias.testwise.repository.PokemonRepository
import kotlinx.android.synthetic.main.fragment_list.rv_pokemon_list as recyclerView
import kotlinx.android.synthetic.main.fragment_list.tv_error_message as errorMessageTextView
import kotlinx.android.synthetic.main.fragment_list.view.*

class PokemonsFragment : Fragment() {
    private lateinit var pokeAdapter: PokeListAdapter
    private lateinit var pokemonVm: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokeAdapter = PokeListAdapter()
        pokemonVm = ViewModelProviders.of(activity)
                .get(PokemonListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        with(root.rv_pokemon_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = pokeAdapter
        }

        subscribe(pokeAdapter, root)

        return root
    }

    private fun subscribe(myAdapter: PokeListAdapter, root: View ) {
        pokemonVm = ViewModelProviders.of(this)
                .get(PokemonListViewModel::class.java)

        pokemonVm.list.observe(this, Observer { data ->
            myAdapter.dataSource = data!!
        })

        pokemonVm.status.observe(this, Observer { newStatus ->
            when (newStatus) {
                PokemonRepository.Status.STARTED -> {
                    root.rv_pokemon_list.visibility = View.GONE
                    root.tv_error_message.visibility = View.GONE
                    root.progressBar.visibility = View.VISIBLE
                }
                PokemonRepository.Status.FAILED -> {
                    root.rv_pokemon_list.visibility = View.VISIBLE
                    root.progressBar.visibility = View.GONE
                }
                PokemonRepository.Status.SUCCESS -> {
                    root.rv_pokemon_list.visibility = View.VISIBLE
                    root.progressBar.visibility = View.GONE
                }
                else -> {
                    root.rv_pokemon_list.visibility = View.GONE
                    root.progressBar.visibility = View.GONE
                    root.tv_error_message.visibility = View.GONE
                }
            }
        })
    }

}