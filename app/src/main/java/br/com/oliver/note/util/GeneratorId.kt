package br.com.oliver.note.util

import java.util.UUID

class GeneratorId {
    fun uuid() = UUID.randomUUID().toString()
}