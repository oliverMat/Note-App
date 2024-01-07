package br.com.oliver.note.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.oliver.note.databinding.ItemTaskRecyclerBinding
import br.com.oliver.note.model.TaskModel

class TaskListAdapter(private var datalist: List<TaskModel>) :
    RecyclerView.Adapter<TaskListAdapter.UserHolder>() {

    class UserHolder(private val binding: ItemTaskRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: TaskModel) {
            binding.email.text = get.title
            binding.name.text = get.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ItemTaskRecyclerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }


    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}