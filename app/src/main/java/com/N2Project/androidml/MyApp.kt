package com.N2Project.androidml
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainPage") {
        composable("MainPage") { MainPage(navController) }
        composable("CatNDog") { CatNDog() }
    }
}
