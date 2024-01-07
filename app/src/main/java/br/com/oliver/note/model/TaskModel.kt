package br.com.oliver.note.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.oliver.note.util.GeneratorId
import java.io.Serializable

@Entity
data class TaskModel(
    @PrimaryKey val id: String = GeneratorId().uuid(),
    var title: String
) : Serializable
