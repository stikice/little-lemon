package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.screen.Home

@Composable
fun MyNavigation(navController: NavHostController, sharedPreferences: SharedPreferences) {
    NavHost(navController = navController, startDestination = if(sharedPreferences.contains("firstName")) com.example.littlelemon.navigation.Home.route else com.example.littlelemon.navigation.Onboarding.route){
        composable(com.example.littlelemon.navigation.Onboarding.route){
            Onboarding(navController)
        }
        composable(com.example.littlelemon.navigation.Home.route){
            Home(navController)
        }
        composable(com.example.littlelemon.navigation.Profile.route){
            Profile(navController)
        }

    }
}