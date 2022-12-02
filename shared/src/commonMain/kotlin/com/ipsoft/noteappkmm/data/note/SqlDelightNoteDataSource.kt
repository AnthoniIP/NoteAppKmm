package com.ipsoft.noteappkmm.data.note

import com.ipsoft.noteappkmm.database.NoteDatabase
import com.ipsoft.noteappkmm.domain.note.Note
import com.ipsoft.noteappkmm.domain.note.NoteDataSource
import com.ipsoft.noteappkmm.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(db: NoteDatabase) : NoteDataSource {

    private val noteQueries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        noteQueries.insertNote(
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            createdDate = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteQueries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteQueries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }


    override suspend fun deleteNoteById(id: Long) {
        noteQueries.deleteNoteById(id)
    }
}