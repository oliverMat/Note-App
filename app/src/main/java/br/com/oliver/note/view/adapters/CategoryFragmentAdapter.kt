package br.com.oliver.note.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.oliver.note.model.Category
import br.com.oliver.note.util.PagerDiffUtil
import br.com.oliver.note.view.FirstFragment

class CategoryFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val table: MutableList<Category> = ArrayList()

    override fun getItemCount(): Int = table.size

    override fun createFragment(position: Int): Fragment {
        val listTable: Category = table[position]
        return FirstFragment.newInstance(listTable.name)
    }

    fun getPositionName(position: Int): String = table[position].name

    fun addFragment(category: List<Category>) {

        val callback = PagerDiffUtil(table, category)
        val diff = DiffUtil.calculateDiff(callback)
        table.clear()
        table.addAll(category)
        diff.dispatchUpdatesTo(this)

    }
}