package br.com.oliver.note.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.util.PagerDiffUtil
import br.com.oliver.note.view.FirstFragment

class ListFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val table: MutableList<ListModel> = ArrayList()

    override fun getItemCount(): Int = table.size

    override fun createFragment(position: Int): Fragment {
        val listModelTable: ListModel = table[position]
        return FirstFragment.newInstance(listModelTable.name)
    }

    fun getPositionName(position: Int): String = table[position].name

    fun addFragment(listModels: List<ListModel>) {

        val callback = PagerDiffUtil(table, listModels)
        val diff = DiffUtil.calculateDiff(callback)
        table.clear()
        table.addAll(listModels)
        diff.dispatchUpdatesTo(this)

    }
}