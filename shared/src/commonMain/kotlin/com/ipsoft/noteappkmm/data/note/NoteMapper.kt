package com.ipsoft.noteappkmm.data.note

import com.ipsoft.noteappkmm.domain.note.Note
import comipsoftnoteappkmm.database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    colorHex = colorHex,
    created = Instant.fromEpochMilliseconds(createdDate)
        .toLocalDateTime(TimeZone.currentSystemDefault()))