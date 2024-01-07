package br.com.oliver.note.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.oliver.note.databinding.ItemTaskRecyclerBinding
import br.com.oliver.note.model.TaskModel
import br.com.oliver.note.view.adapters.TaskListAdapter.TaskViewHolder

class TaskListAdapter : ListAdapter<TaskModel, TaskViewHolder>(TASK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TaskViewHolder(private val binding: ItemTaskRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: TaskModel) {
            binding.email.text = get.title
            binding.name.text = get.title

        }

        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                val binding = ItemTaskRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TaskViewHolder(binding)
            }
        }

    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<TaskModel>() {
            override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}