package br.com.oliver.note.model.repo

import androidx.annotation.WorkerThread
import br.com.oliver.note.model.TaskModel
import br.com.oliver.note.model.interfaces.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    @WorkerThread
    suspend fun insert(taskModel: TaskModel) {
        taskDao.insert(taskModel)
    }

    @WorkerThread
    suspend fun update(taskModel: TaskModel) {
        taskDao.update(taskModel)
    }

    @WorkerThread
    suspend fun rename(oldName : String, newName: String) {
        taskDao.rename(oldName, newName)
    }

    @WorkerThread
    suspend fun delete(taskModel: TaskModel) {
        taskDao.delete(taskModel)
    }

    val allTaskModel: Flow<List<TaskModel>> = taskDao.getTask()

}