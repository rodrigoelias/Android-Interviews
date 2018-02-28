package list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.rodrigoelias.testwise.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pokemons: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myAdapter = PokeListAdapter()

        with(content_list_recyclerview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = myAdapter
        }

        pokemons = ViewModelProviders.of(this)
                .get(PokemonListViewModel::class.java)

        pokemons.list.observe(this, Observer {
                it?.let { myAdapter.dataSource = it }
        })
    }
}
