package com.kotlin.worker.smartstudy.presentation.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.worker.smartstudy.presentation.component.DeleteDialog
import com.kotlin.worker.smartstudy.presentation.component.StudySessions
import com.kotlin.worker.smartstudy.presentation.component.SubjectListBottomSheet
import com.kotlin.worker.smartstudy.sessions
import com.kotlin.worker.smartstudy.subjects
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun SessionScreenRoute(navigator: DestinationsNavigator) {
    val viewModel:SessionViewModel = hiltViewModel()
    SessionScreen(
        onBackButtonClick = { navigator.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(onBackButtonClick: () -> Unit) {

    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subjects,
        bottomSheetTitle = "Select Subject",
        onSubjectClicked = {
            // isBottomSheetOpen = false
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible)
                    isBottomSheetOpen = false
            }
        },
        onDismissRequest = { isBottomSheetOpen = false }
    )
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    DeleteDialog(
        title = "Delete Session?",
        isOpen = isDeleteDialogOpen,
        bodyText = "Are you sure you want to delete this Session? \n this action can not be undone",
        onConfirmButtonClick = { isDeleteDialogOpen = false },
        onDismissRequest = { isDeleteDialogOpen = false }
    )
    Scaffold(
        topBar = { SessionScreenTopBar(onBackButtonClick = {onBackButtonClick()}) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TimerSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            item {
                RelatedToSubject(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedToSubject = "English",
                    onDropDownClick = { isBottomSheetOpen = true })
            }
            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    startButtonClick = {},
                    cancelButtonClick = { TODO() },
                    finishButtonClick = { TODO() }
                )
            }
            StudySessions(
                sectionTitle = "STUDY SESSIONS HISTORY",
                sessions = sessions,
                onDeleteIconClick = { isDeleteDialogOpen = true }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreenTopBar(onBackButtonClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "")
            }
        },
        title = { Text(text = "Study Session", style = MaterialTheme.typography.headlineSmall) },

        )

}

@Composable
fun TimerSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(
            text = "00:53:55",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
        )
    }
}

@Composable
fun RelatedToSubject(
    modifier: Modifier = Modifier,
    relatedToSubject: String,
    onDropDownClick: () -> Unit
) {

    Column(modifier = modifier) {
        Text(
            text = "Related to Subject",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = relatedToSubject,
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = onDropDownClick) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown"
                )
            }
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = cancelButtonClick) {
            Text(
                modifier = Modifier.padding(10.dp, 5.dp),
                text = "Cancel"
            )
        }
        Button(onClick = startButtonClick) {
            Text(
                modifier = Modifier.padding(10.dp, 5.dp),
                text = "Start"
            )
        }
        Button(onClick = finishButtonClick) {
            Text(
                modifier = Modifier.padding(10.dp, 5.dp),
                text = "Finish"
            )
        }
    }

}
