package com.rodrigoelias.testwise

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.rodrigoelias.testwise.repository.PokemonRepository
import com.rodrigoelias.testwise.list.PokeListAdapter
import com.rodrigoelias.testwise.list.PokemonListViewModel
import com.rodrigoelias.testwise.list.PokemonsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (null == savedInstanceState) {
            initFragment( PokemonsFragment())
        }
    }

    private fun initFragment(notesFragment: Fragment) {
        // Add the NotesFragment to the layout
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, notesFragment)
        transaction.commit()
    }
}
