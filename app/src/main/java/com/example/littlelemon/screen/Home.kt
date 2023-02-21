package com.example.littlelemon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.data.MenuDatabase
import com.example.littlelemon.data.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonColor

private var clicked = false

@Composable
fun Home(navController: NavHostController) {
    val context = LocalContext.current
    val database = MenuDatabase.getDatabase(context)
    val menuItems by database.menuDao().getAllMenuItems().observeAsState()
    var searchPhrase by remember {
        mutableStateOf("")
    }
    var menuToShow by remember {
        mutableStateOf(listOf<MenuItem>())
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(20.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.5f)
            )

            IconButton(onClick = { navController.navigate(com.example.littlelemon.navigation.Profile.route) }) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .background(LittleLemonColor.green)
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.upper_title),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonColor.yellow
            )
            Row(
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Column() {
                    Text(
                        text = stringResource(id = R.string.upper_location),
                        fontSize = 24.sp,
                        color = LittleLemonColor.cloud,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(id = R.string.upper_description),
                        color = LittleLemonColor.cloud,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(bottom = 28.dp)
                            .fillMaxWidth(0.6f)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.hero),
                    contentDescription = "Upper Panel Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )
            }

            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                shape = RoundedCornerShape(20.dp),
                leadingIcon = { Icon(Icons.Default.Search, "", tint = LittleLemonColor.charcoal) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(stringResource(id = R.string.upper_search_hint)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LittleLemonColor.cloud,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        val categoriesHashMap = menuItems?.groupBy { it.category }.orEmpty()

        if (searchPhrase.isNotBlank()) {
            clicked = false
            menuToShow = menuItems?.filter { it.title.lowercase().contains(searchPhrase) }.orEmpty()
        }

        if (searchPhrase.isBlank() && !clicked) {
            menuToShow = menuItems.orEmpty()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonColor.charcoal,
                text = stringResource(id = R.string.categories_title)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Start
            ) {
                for (i in categoriesHashMap.keys) {
                    TextButton(
                        onClick = {
                            menuToShow = categoriesHashMap[i].orEmpty()
                            clicked = true
                        },
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = LittleLemonColor.cloud
                        ),
                        modifier = Modifier.padding(0.dp, 12.dp, 12.dp, 0.dp)
                    ) {
                        Text(i)
                    }
                }
            }

        }

        Divider(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            thickness = 0.5.dp,
            color = LittleLemonColor.charcoal
        )

        MenuItems(menuToShow)
    }
}