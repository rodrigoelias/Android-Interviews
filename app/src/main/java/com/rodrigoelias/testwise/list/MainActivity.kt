package com.rodrigoelias.testwise.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigoelias.testwise.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity(), Callback<PokeAPIResponse> {

    lateinit var pokemons: PokemonListViewModel

    override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: Call<PokeAPIResponse>, response: Response<PokeAPIResponse>) {
        if (response.isSuccessful) {
            var s = response.body()

            s?.let{ s.pokemon.forEach { println("current score: ${it.name}") }}
        } else {
            System.out.println(response.errorBody());
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemons = ViewModelProviders.of(this).get(PokemonListViewModel::class.java)
        var myAdapter = PokeListAdapter()

        with(content_list_recyclerview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = myAdapter
        }

        pokemons.list.observe(this, Observer {
                it?.let { myAdapter.dataSource = it }
        })

        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co//api/v1/pokedex/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(CreditScoreService::class.java)

        val s = service.getScore()
        //s.enqueue(this)
    }
}

interface CreditScoreService{
    @GET("1/")
    fun getScore(): Call<PokeAPIResponse>
}

data class Pokemon(val name: String, val resourceUri: String)
data class PokeAPIResponse(val pokemon: List<Pokemon>)

class PokeListAdapter : RecyclerView.Adapter<PokeListAdapter.ViewHolder>(){

    var dataSource : List<Pokemon> = emptyList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("onBindViewHolder")
        holder.itemView.tv_item_title.text = getItem(position).name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item,
                        parent,
                        false)

        println("onCreateViewHolder")
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    private fun getItem(position: Int) = dataSource[position]

}
