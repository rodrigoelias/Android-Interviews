package com.rodrigoelias.testwise.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rodrigoelias.testwise.R
import com.rodrigoelias.testwise.data.Pokemon
import kotlinx.android.synthetic.main.list_item.view.*
import java.security.AccessController.getContext
import kotlinx.android.synthetic.main.list_item.view.tv_item_title as cardTitleTextView

class PokeListAdapter : RecyclerView.Adapter<PokeListAdapter.ViewHolder>() {

    var dataSource: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardTitleTextView.text = getItem(position).toString()
        Glide.with(holder.itemView.poke_image)
                .load("https://www.serebii.net/art/th/${getItem(position).Id}.png")
                .thumbnail()
                .into(holder.itemView.poke_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item,
                        parent,
                        false)

        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun getItem(position: Int) = dataSource[position]

}
