package com.ipsoft.noteappkmm.domain.note

import com.ipsoft.noteappkmm.presentation.babyBlueHex
import com.ipsoft.noteappkmm.presentation.lightGreenHex
import com.ipsoft.noteappkmm.presentation.redOrangeHex
import com.ipsoft.noteappkmm.presentation.redPinkHex
import com.ipsoft.noteappkmm.presentation.violetHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime,
) {
    companion object {
        private val colors = listOf(redOrangeHex, redPinkHex, violetHex, babyBlueHex, lightGreenHex)
        fun randomColor() = colors.random()
    }
}
