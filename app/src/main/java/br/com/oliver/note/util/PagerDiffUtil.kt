package br.com.oliver.note.util

import androidx.recyclerview.widget.DiffUtil
import br.com.oliver.note.model.Category

class PagerDiffUtil(private val oldList: List<Category>, private val newList: List<Category>) : DiffUtil.Callback() {

    enum class PayloadKey {
        VALUE
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        return listOf(PayloadKey.VALUE)
    }
}