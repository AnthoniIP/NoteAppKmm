package com.ipsoft.noteappkmm.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                },
                backgroundColor = Color.Black,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White,
                )
            }
        },
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(state.noteColor))
                .padding(padding)
                .padding(16.dp),
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                onValueChanged = viewModel::onNoteTitleChanged,
                isHintVisible = state.isNoteTitleHintVisible,
                onFocusChanged = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter some content...",
                onValueChanged = viewModel::onNoteContentChanged,
                isHintVisible = state.isNoteContentHiltVisible,
                onFocusChanged = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f),
            )
        }
    }
}
