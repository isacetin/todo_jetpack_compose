package com.example.my_application_todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.my_application_todo.enums.TodoSceneRoute
import com.example.my_application_todo.scene.detail.DetailScene
import com.example.my_application_todo.scene.home.HomeScene
import kotlinx.coroutines.flow.toSet


@Composable
fun RootHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = TodoSceneRoute.HOME.name,
    ) {
        composable(route = TodoSceneRoute.HOME.name) {
            HomeScene(
                onNavigateDetail = { id ->
                    navController.navigate(TodoSceneRoute.DETAIL.name + "?taskId=${id}")
                },
            )
        }
        composable(
            route = TodoSceneRoute.DETAIL.name + "?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            DetailScene(
                onNavigationBack = { navController.popBackStack() },
                taskId = backStackEntry.arguments?.getInt("taskId")
            )
        }
    }

}