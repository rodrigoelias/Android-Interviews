package com.rodrigoelias.testwise

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Callback<Score> {
    override fun onFailure(call: Call<Score>?, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: Call<Score>?, response: Response<Score>?) {
        if(response!!.isSuccessful) {
            var s = response.body()

            if(s != null) {
                println("current score: ${s.creditReportInfo.score} - max score: ${s.creditReportInfo.maxScoreValue}")
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    private val action = View.OnClickListener(){it.tv_item_title.text = "Ronaldo"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val items = listOf("Oi", "Manhe")

//        with(content_list_recyclerview){
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(this.context)
//            adapter = MyAdapter(items, action)
//        }
        val retrofit = Retrofit.Builder()
                .baseUrl("https://5lfoiyb0b3.execute-api.us-west-2.amazonaws.com/prod/mockcredit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(CreditScoreService::class.java)

        val s = service.getScore()
        s.enqueue(this)
    }
}

interface CreditScoreService{
    @GET("values")
    fun getScore(): Call<Score>
}

data class CreditReportInfo(val score: Int, val maxScoreValue: Int)
data class Score(val creditReportInfo :CreditReportInfo)

class MyAdapter(private val items: List<String>,
                private val onClickHandler: View.OnClickListener) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_item_title.text = items[position]
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
