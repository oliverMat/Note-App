package br.com.oliver.note.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.oliver.note.model.Category
import br.com.oliver.note.model.data.NoteRoomDatabase
import br.com.oliver.note.model.repo.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.*
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CategoryViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : CategoryRepository
    private val applicationScope = CoroutineScope(SupervisorJob())

    init {
        val dao = NoteRoomDatabase.getDatabase(application, applicationScope).getCategoryDao()
        repository = CategoryRepository(dao)
    }

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