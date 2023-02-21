package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(
        "LittleLemon.prefs", Context.MODE_PRIVATE
    )
    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.padding(40.dp))
        Text(
            text = stringResource(id = R.string.onboarding_info_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            color = LittleLemonColor.charcoal,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = firstName, onValueChange = {}, label = { Text(text = "First name") }, enabled = false)
            OutlinedTextField(value = lastName, onValueChange = {}, label = { Text(text = "Last name") }, enabled = false)
            OutlinedTextField(value = email, onValueChange = {}, label = { Text(text = "Email") }, enabled = false)
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
            shape = RoundedCornerShape(percent = 20),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                sharedPreferences.edit()
                    .clear()
                    .apply()
                navController.navigate(com.example.littlelemon.navigation.Onboarding.route)
            },
        ) {
            Text(color = LittleLemonColor.charcoal, text = "Log out")
        }

    }

}