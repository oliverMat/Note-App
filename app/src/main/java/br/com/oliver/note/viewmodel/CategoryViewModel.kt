package br.com.oliver.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.oliver.note.model.Category
import br.com.oliver.note.model.repo.CategoryRepository
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }

    fun rename(nomeTable: String, novoNome: String) = viewModelScope.launch {
        repository.rename(nomeTable, novoNome)
    }

    fun delete(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }

    val allCategory: LiveData<List<Category>> = repository.allCategory.asLiveData()
}

class CategoryViewModelFactory(private val repository: CategoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}