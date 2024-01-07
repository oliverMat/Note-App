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

    @Query("UPDATE or IGNORE taskmodel SET title=:oldNome WHERE title=:newName")
    suspend fun rename(newName: String, oldNome: String)

    @Delete
    suspend fun delete(taskModel: TaskModel)

    @Query("SELECT * FROM TaskModel")
    fun getTask(): Flow<List<TaskModel>>
}