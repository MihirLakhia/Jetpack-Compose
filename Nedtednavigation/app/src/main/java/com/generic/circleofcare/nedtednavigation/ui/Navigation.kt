package com.generic.circleofcare.nedtednavigation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.generic.circleofcare.nedtednavigation.DetailScreen
import com.generic.circleofcare.nedtednavigation.MainScreen
import com.generic.circleofcare.nedtednavigation.Screen

@Composable
fun Navigation(){
    val navController  = rememberNavController()
    NavHost(navController = navController, Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController)
        }
        composable(route = Screen.DetailScreen.route +"?name = {name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "Mihir"
                    nullable = true
                }
            )){entry->
                DetailScreen(name = entry.arguments?.getString("name"),navController)
        }
    }
}

