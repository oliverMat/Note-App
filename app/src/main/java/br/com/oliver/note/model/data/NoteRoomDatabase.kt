package br.com.oliver.note.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.model.TaskModel
import br.com.oliver.note.model.interfaces.ListDao
import br.com.oliver.note.model.interfaces.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ListModel::class, TaskModel::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun getListDao(): ListDao
    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NoteRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    NoteRoomDatabase::class.java,"Note")
                    .fallbackToDestructiveMigration()
                    .addCallback(NoteRoomDbCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }

        private class NoteRoomDbCallback(
            private val scope: CoroutineScope
        ) : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.getListDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(listDao: ListDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            val listModel = ListModel(name = "My tasks", main = true)
            listDao.insert(listModel)

        }
    }

}
