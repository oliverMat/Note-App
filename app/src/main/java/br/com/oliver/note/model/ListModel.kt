package br.com.oliver.note.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListModel(@PrimaryKey val name: String)