package me.leig.qrcode

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

class ListViewHodler(view: View) : RecyclerView.ViewHolder(view) {

    var content: TextView = view.findViewById(R.id.tv_content)

}