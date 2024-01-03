package br.com.oliver.note.model.repo

import androidx.annotation.WorkerThread
import br.com.oliver.note.model.Category
import br.com.oliver.note.model.interfaces.CategoryDao
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    @WorkerThread
    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    @WorkerThread
    suspend fun rename(nomeTable : String, novoNome: String) {
        categoryDao.rename(nomeTable, novoNome)
    }

    @WorkerThread
    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    val allCategory: Flow<List<Category>> = categoryDao.getCategory()
}