package list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigoelias.testwise.R
import data.Pokemon
import kotlinx.android.synthetic.main.list_item.view.tv_item_title as cardTitleTextView

class PokeListAdapter : RecyclerView.Adapter<PokeListAdapter.ViewHolder>() {

    var dataSource: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardTitleTextView.text = getItem(position).name
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
