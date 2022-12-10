package com.ipsoft.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipsoft.noteappkmm.domain.note.Note
import com.ipsoft.noteappkmm.domain.note.NoteDataSource
import com.ipsoft.noteappkmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(NOTE_TITLE, "")
    private val isNoteTitleTextFocused =
        savedStateHandle.getStateFlow(IS_NOTE_TITLE_FOCUSED, false)
    private val noteContent = savedStateHandle.getStateFlow(NOTE_CONTENT, "")
    private val isNoteContentTextFocused =
        savedStateHandle.getStateFlow(IS_NOTE_CONTENT_FOCUSED, false)
    private val noteColor = savedStateHandle.getStateFlow(NOTE_COLOR, Note.randomColor())

    val state = combine(
        noteTitle,
        isNoteTitleTextFocused,
        noteContent,
        isNoteContentTextFocused,
        noteColor,
    ) { title, noteTitleFocused, noteContent, noteContentTextFocused, color ->

        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && noteTitleFocused,
            noteContent = noteContent,
            isNoteContentHiltVisible = noteContent.isEmpty() && noteContentTextFocused,
            noteColor = color,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle[NOTE_TITLE] = note.title
                    savedStateHandle[NOTE_CONTENT] = note.content
                    savedStateHandle[NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[NOTE_TITLE] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[NOTE_TITLE] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle[IS_NOTE_TITLE_FOCUSED] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle[IS_NOTE_CONTENT_FOCUSED] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now(),
                ),
            )
            _hasNoteBeenSaved.value = true
        }
    }
}
