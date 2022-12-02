package com.ipsoft.noteappkmm.data.note

import com.ipsoft.noteappkmm.domain.note.Note
import com.ipsoft.noteappkmm.domain.time.DateTimeUtil

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {

        if (query.isBlank()) return notes
        return notes.filter {
            it.title.trim().contains(query, true) || it.content.trim().contains(query, true)
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }

    }
}