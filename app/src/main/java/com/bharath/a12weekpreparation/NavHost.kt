package com.bharath.a12weekpreparation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bharath.a12weekpreparation.presentation.MainScreen
import com.bharath.a12weekpreparation.presentation.ProblemScreen

@Composable
fun MyNavHost(
    navHostController: NavHostController,
) {


    NavHost(navController = navHostController, startDestination = "Home", builder = {
        composable("Home") {
            MainScreen(navHostController = navHostController)
        }
        composable("Problem" + "/{id}", enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(500))
        },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(500))
            }
        ) {
            ProblemScreen(navHostController = navHostController)
        }
    })
}