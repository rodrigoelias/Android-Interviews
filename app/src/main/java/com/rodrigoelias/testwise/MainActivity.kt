package com.rodrigoelias.testwise

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity(), Callback<PokeAPIResponse> {
    override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: Call<PokeAPIResponse>?, response: Response<PokeAPIResponse>?) {
        if(response!!.isSuccessful) {
            var s = response.body()

            if(s != null) {
                s.pokemon.forEach{println("current score: ${it.name}")}
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    private val action = View.OnClickListener(){it.tv_item_title.text = "Clicked"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(Pokemon("Bulbasaur","1"), Pokemon("Ivysaur", "2"))

        with(content_list_recyclerview){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = MyAdapter(items, action)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co//api/v1/pokedex/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(CreditScoreService::class.java)

        val s = service.getScore()
        s.enqueue(this)
    }
}

interface CreditScoreService{
    @GET("1/")
    fun getScore(): Call<PokeAPIResponse>
}

data class Pokemon(val name: String, val resourceUri: String)
data class PokeAPIResponse(val pokemon: List<Pokemon>)

class MyAdapter(private val items: List<Pokemon>,
                private val onClickHandler: View.OnClickListener) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_item_title.text = items[position].name
        holder.itemView.setOnClickListener(onClickHandler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item,
                        parent,
                        false)

        return ViewHolder(view)
    }
}
