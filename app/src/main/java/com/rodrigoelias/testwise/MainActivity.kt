package com.rodrigoelias.testwise

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.content_list_recyclerview as recyclerView
import kotlinx.android.synthetic.main.activity_main.tv_error_message as errorMessageTextView
import kotlinx.android.synthetic.main.activity_main.progressBar
import list.PokeListAdapter
import list.PokemonListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var pokemons: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myAdapter = PokeListAdapter()

        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = myAdapter
            setItemViewCacheSize(25)
        }

        subscribe(myAdapter)
    }

    private fun subscribe(myAdapter: PokeListAdapter) {
        pokemons = ViewModelProviders.of(this)
                .get(PokemonListViewModel::class.java)

        pokemons.list.observe(this, Observer {
            it?.let { myAdapter.dataSource = it }
        })

        pokemons.status.observe(this, Observer {
            it?.let {
                when (it) {
                    PokemonListViewModel.Status.STARTED -> {
                        recyclerView.visibility = View.GONE
                        errorMessageTextView.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    PokemonListViewModel.Status.FAILED -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    PokemonListViewModel.Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }
}
