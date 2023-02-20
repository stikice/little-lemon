package com.example.littlelemon

import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Onboarding(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(
        "LittleLemon.prefs", MODE_PRIVATE)
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
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

        Text(
            text = stringResource(id = R.string.onboarding_title),
            modifier = Modifier
                .background(LittleLemonColor.green)
                .fillMaxWidth()
                .padding(40.dp),
            color = LittleLemonColor.cloud,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.onboarding_info_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            color = LittleLemonColor.charcoal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(singleLine = true, value = firstName, onValueChange = {firstName = it}, label = { Text(text = "First name") })
            OutlinedTextField(singleLine = true, value = lastName, onValueChange = {lastName = it}, label = { Text(text = "Last name") })
            OutlinedTextField(singleLine = true, value = email, onValueChange = {email = it}, label = { Text(text = "Email") })
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
            shape = RoundedCornerShape(percent = 20),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                if(firstName.isBlank() || lastName.isBlank() || email.isBlank()){
                    Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    sharedPreferences.edit()
                        .putString("firstName", firstName)
                        .putString("lastName", lastName)
                        .putString("email", email)
                        .apply()
                    navController.navigate(Home.route)
                }
            },
        ) {
            Text(color = LittleLemonColor.charcoal, text = "Register")
        }
    }
}