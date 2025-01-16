package com.N2Project.androidml
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun MyApp(applicationContext: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainPage") {
        composable("MainPage") { MainPage(navController) }
        composable("CatNDog") { CatNDog(context = applicationContext) }
    }
}
