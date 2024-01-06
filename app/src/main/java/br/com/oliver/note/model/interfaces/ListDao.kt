package br.com.oliver.note.model.interfaces

import androidx.room.*
import br.com.oliver.note.model.ListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: ListModel)

    @Update
    suspend fun update(category: ListModel)

    @Query("UPDATE or IGNORE listmodel SET name=:oldNome WHERE name=:newName")
    suspend fun rename(newName: String, oldNome: String)

    @Delete
    suspend fun delete(category: ListModel)

    @Query("SELECT * FROM ListModel")
    fun getList(): Flow<List<ListModel>>
}