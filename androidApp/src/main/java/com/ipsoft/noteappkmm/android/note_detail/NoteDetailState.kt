package com.ipsoft.noteappkmm.android.note_detail

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintVisible: Boolean = false,
    val noteContent: String = "",
    val isNoteContentHiltVisible: Boolean = false,
    val noteColor: Long = 0xFFFFFF,
)
