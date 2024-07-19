package com.example.my_application_todo.scene.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_application_todo.R
import com.example.my_application_todo.ui.theme.KoyuYesil
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.my_application_todo.data_source.TodoDAO
import com.example.my_application_todo.data_source.TodoEntity
import com.example.my_application_todo.repository.TodoRepositoryImpl
import com.example.my_application_todo.scene.home.views.TodoTaskItem
import kotlinx.coroutines.launch


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScene(
    onNavigateDetail: (id: Int?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val contents by viewModel.todos.collectAsStateWithLifecycle()
    val updatedTask by viewModel.updatedTask.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.getAllTodos()
    }

    LaunchedEffect(key1 = updatedTask) {
        launch {
            if (updatedTask.id != null) {
                val result = snackbarHostState
                    .showSnackbar(
                        if (updatedTask.isCompleted) {
                            context.getString(R.string.updated_snackbar_completed)
                                .replace("%1", updatedTask.id.toString())
                        } else {
                            context.getString(R.string.updated_snackbar_not_completed)
                                .replace("%1", updatedTask.id.toString())
                        },
                        actionLabel = "Geri Al",
                    )

                when (result) {
                    SnackbarResult.Dismissed -> TODO()
                    SnackbarResult.ActionPerformed -> viewModel.updateTodo(
                        TodoEntity(
                            updatedTask.id,
                            updatedTask.content,
                            !updatedTask.isCompleted
                        )
                    )
                }
            }

        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeViewTopBar(
                onClickDrawer = {},
                onClickFilter = {},
                onClickMenu = {},
            )
        }, content = {
            HomeViewContent(paddingValues = it, contents = contents, viewModel, onNavigateDetail)
        }, floatingActionButton = {
            HomeFloatingActionButton(onClick = onNavigateDetail)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewTopBar(
    onClickDrawer: () -> Unit,
    onClickFilter: () -> Unit,
    onClickMenu: () -> Unit,
) {
    Surface(shadowElevation = 2.dp) {
        TopAppBar(
            modifier = Modifier,
            title = {
                Text(
                    text = stringResource(id = R.string.home_top_bar_title)
                )
            },
            actions = {
                IconButton(
                    onClick = onClickFilter
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_filter_icon),
                        contentDescription = "filter"
                    )
                }

                IconButton(
                    onClick = onClickMenu
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_menu_icon),
                        contentDescription = "filter"
                    )
                }


            },
            navigationIcon = {
                IconButton(
                    onClick = onClickDrawer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_drawable_icon),
                        contentDescription = "menu"
                    )
                }
            }
        )
    }


}

@Composable
fun HomeViewContent(
    paddingValues: PaddingValues,
    contents: List<TodoEntity>,
    viewModel: HomeViewModel,
    onNavigateDetail: (id: Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        LazyColumn(
            content = {

                item {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "All Tasks",
                        style = TextStyle(fontSize = 22.sp)
                    )
                }

                items(contents.size) {
                    val item = contents[it]
                    TodoTaskItem(
                        checked = item.isCompleted,
                        onCheckedChange = { isChecked ->
                            viewModel.updateTodo(TodoEntity(item.id, item.content, isChecked))
                        },
                        title = item.content,
                        onNavigationToDetail = { onNavigateDetail(it) }
                    )
                }
            }
        )

    }
}

@Composable
fun HomeFloatingActionButton(onClick: (Int?) -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick.invoke(-1)
        },
        containerColor = KoyuYesil,
        shape = CircleShape,
        contentColor = Color.White
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = "add",
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeContentPreview() {
    val generatedContent = listOf<TodoEntity>(
        TodoEntity(0, "TODO1", false),
        TodoEntity(1, "TODO2", true)
    )
    HomeViewContent(
        paddingValues = PaddingValues(2.dp), contents = generatedContent, hiltViewModel(), {}
    )

}