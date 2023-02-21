package com.example.littlelemon.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.screen.Onboarding
import com.example.littlelemon.screen.Profile
import com.example.littlelemon.screen.Home

@Composable
fun MyNavigation(navController: NavHostController, sharedPreferences: SharedPreferences) {
    NavHost(navController = navController, startDestination = if(sharedPreferences.contains("firstName")) Home.route else Onboarding.route){
        composable(Onboarding.route){
            Onboarding(navController)
        }
        composable(Home.route){
            Home(navController)
        }
        composable(Profile.route){
            Profile(navController)
        }

    }
}