package com.ipsoft.noteappkmm.android.note_list

import com.ipsoft.noteappkmm.domain.note.Note

data class NoteListState(
    val isSearchActive: Boolean = false,
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
)