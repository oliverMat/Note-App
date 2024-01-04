package br.com.oliver.note

import android.app.Application
import br.com.oliver.note.model.data.NoteRoomDatabase
import br.com.oliver.note.model.repo.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Application : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CategoryRepository(database.getCategoryDao()) }
}