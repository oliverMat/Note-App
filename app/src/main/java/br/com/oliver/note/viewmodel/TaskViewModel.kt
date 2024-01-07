package br.com.oliver.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.oliver.note.model.TaskModel
import br.com.oliver.note.model.repo.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun insert(taskModel: TaskModel) = viewModelScope.launch {
        taskRepository.insert(taskModel)
    }

    fun update(taskModel: TaskModel) = viewModelScope.launch {
        taskRepository.update(taskModel)
    }

    fun rename(oldName: String, newName: String) = viewModelScope.launch {
        taskRepository.rename(oldName, newName)
    }

    fun delete(taskModel: TaskModel) = viewModelScope.launch {
        taskRepository.delete(taskModel)
    }

    val allTask: LiveData<List<TaskModel>> = taskRepository.allTaskModel.asLiveData()
}

class TaskViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}