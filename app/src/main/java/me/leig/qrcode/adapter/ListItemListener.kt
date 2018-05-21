package me.leig.qrcode.adapter

import android.view.View
import java.text.FieldPosition

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

interface ListItemListener {
    fun itemOnClickListener(view: View, position: Int)
    fun itemOnLongClickListener(view: View, position: Int)
}