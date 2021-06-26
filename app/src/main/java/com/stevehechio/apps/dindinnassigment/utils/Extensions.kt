package com.stevehechio.apps.dindinnassigment.utils

import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by stevehechio on 6/26/21
 */

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

fun View.gone(): View {
    if (visibility != View.GONE){
        visibility = View.GONE
    }
    return this
}
fun View.invisible(): View {
    if (visibility != View.INVISIBLE){
        visibility = View.INVISIBLE
    }
    return this
}

fun View.visible(): View {
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}