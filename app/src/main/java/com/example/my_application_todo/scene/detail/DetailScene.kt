package com.example.my_application_todo.scene.detail


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.my_application_todo.R
import com.example.my_application_todo.ui.theme.KoyuYesil

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScene(
    onNavigationBack: () -> Unit,
    taskId: Int?,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    var contentString by remember { mutableStateOf("") }
    val todo by viewModel.todo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        if (taskId != -1 && taskId != null) {
            viewModel.getTodoById(taskId)
        }
    }

    LaunchedEffect(key1 = todo) {
        contentString = todo?.content ?: ""
    }


    Scaffold(
        topBar = {
            DetailSceneTopBar(onNavigationBack)
        },
        content = {
            DetailSceneContent(it,
                value = contentString,
                onChangeValue = { string ->
                    contentString = string
                }
            )
        },
        floatingActionButton = {
            DetailSceneFloatingActionBar(
                onClick = {
                    if (todo == null) {
                        viewModel.insertTodo(contentString)
                    } else {
                        todo?.copy(content = contentString)
                            ?.let {
                                viewModel.updateTodo(it)
                            }
                    }
                    onNavigationBack.invoke()
                }
            )
        },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailSceneTopBar(onNavigationBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.detail_top_bar_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigationBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
    )
}

@Composable
private fun DetailSceneContent(
    paddingValues: PaddingValues,
    value: String,
    onChangeValue: (String) -> Unit
) {
    Surface {
        TextField(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .height(100.dp),

            value = value,
            onValueChange = { onChangeValue.invoke(it) }
        )
    }
}

@Composable
private fun DetailSceneFloatingActionBar(onClick: () -> Unit) {
    Box(Modifier.imePadding()) {
        FloatingActionButton(
            onClick = onClick,
            contentColor = Color.White,
            containerColor = KoyuYesil,
            shape = CircleShape,
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "done"
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DetailSceenPreview() {
    DetailScene({}, -1)
}