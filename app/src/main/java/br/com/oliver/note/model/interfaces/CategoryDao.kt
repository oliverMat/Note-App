package br.com.oliver.note.model.interfaces

import androidx.room.*
import br.com.oliver.note.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("UPDATE or IGNORE category SET name=:novoNome WHERE name=:nomeTable")
    suspend fun rename(nomeTable: String, novoNome: String)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM Category")
    fun getCategory(): Flow<List<Category>>
}