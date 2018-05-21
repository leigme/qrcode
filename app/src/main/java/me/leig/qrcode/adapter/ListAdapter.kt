package me.leig.qrcode.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

class ListAdapter constructor(private val context: Context, private val layoutId: Int, private val dataList: List<String>): RecyclerView.Adapter<ListViewHodler>() {

    var listItemListener: ListItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHodler {
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return ListViewHodler(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ListViewHodler, position: Int) {
        holder.content.text = dataList[position]
        holder.itemView.setOnClickListener({
            if (null != listItemListener) {
                listItemListener!!.itemOnClickListener(holder.itemView, position)
            }
        })
        holder.itemView.setOnLongClickListener({

            if (null != listItemListener) {
                listItemListener!!.itemOnLongClickListener(holder.itemView, position)
            }

            return@setOnLongClickListener false
        })
    }


}