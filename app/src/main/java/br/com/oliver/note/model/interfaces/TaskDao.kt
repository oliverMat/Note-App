package br.com.oliver.note.model.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.oliver.note.model.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskModel: TaskModel)

    @Update
    suspend fun update(taskModel: TaskModel)

    @Delete
    suspend fun delete(taskModel: TaskModel)

    @Query("SELECT * FROM TaskModel WHERE listId=:id")
    fun getTaskById(id : String): Flow<List<TaskModel>>
}