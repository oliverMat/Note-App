package br.com.oliver.note.util

import androidx.recyclerview.widget.DiffUtil
import br.com.oliver.note.model.ListModel

class PagerDiffUtil(private val oldListModel: List<ListModel>, private val newListModel: List<ListModel>) : DiffUtil.Callback() {

    enum class PayloadKey {
        VALUE
    }

    override fun getOldListSize() = oldListModel.size

    override fun getNewListSize() = newListModel.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListModel[oldItemPosition] == newListModel[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListModel[oldItemPosition].name == newListModel[newItemPosition].name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        return listOf(PayloadKey.VALUE)
    }
}