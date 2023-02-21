package com.example.littlelemon.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun MenuItems(list: List<MenuItem>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        for (i in list) {
            MenuItem(i)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItem) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = menuItem.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LittleLemonColor.charcoal
                )
                Text(
                    text = menuItem.description,
                    color = LittleLemonColor.green,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(.75f)
                )
                Text(
                    text = "$" + menuItem.price,
                    color = LittleLemonColor.green,
                    fontWeight = FontWeight.Bold
                )
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = "",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 0.5.dp,
        color = LittleLemonColor.charcoal
    )
}

@Preview(showBackground = true)
@Composable
fun MenuItemsPreview() {
    MenuItems(
        listOf(
            MenuItem(
                1,
                "Greek Salad",
                "The famous greek of salad",
                "$12.99",
                "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                "starters"
            ),
            MenuItem(
                1,
                "Greek Salad",
                "The famous greek of salad",
                "$12.99",
                "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                "starters"
            )
        )
    )
}