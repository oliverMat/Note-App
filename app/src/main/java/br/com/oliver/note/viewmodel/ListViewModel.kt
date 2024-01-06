package br.com.oliver.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.model.repo.ListRepository
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ListRepository) : ViewModel() {

    fun insert(listModel: ListModel) = viewModelScope.launch {
        repository.insert(listModel)
    }

    fun update(listModel: ListModel) = viewModelScope.launch {
        repository.update(listModel)
    }

    fun rename(oldName: String, newName: String) = viewModelScope.launch {
        repository.rename(oldName, newName)
    }

    fun delete(listModel: ListModel) = viewModelScope.launch {
        repository.delete(listModel)
    }

    val allLists: LiveData<List<ListModel>> = repository.allListModel.asLiveData()
}

class ListViewModelFactory(private val repository: ListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}