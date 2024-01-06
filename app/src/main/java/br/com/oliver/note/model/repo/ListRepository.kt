package br.com.oliver.note.model.repo

import androidx.annotation.WorkerThread
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.model.interfaces.ListDao
import kotlinx.coroutines.flow.Flow

class ListRepository(private val listDao: ListDao) {

    @WorkerThread
    suspend fun insert(listModel: ListModel) {
        listDao.insert(listModel)
    }

    @WorkerThread
    suspend fun update(listModel: ListModel) {
        listDao.update(listModel)
    }

    @WorkerThread
    suspend fun rename(oldName : String, newName: String) {
        listDao.rename(oldName, newName)
    }

    @WorkerThread
    suspend fun delete(listModel: ListModel) {
        listDao.delete(listModel)
    }

    val allListModel: Flow<List<ListModel>> = listDao.getList()
}