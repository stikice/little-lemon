package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.data.MenuDatabase
import com.example.littlelemon.data.MenuItemNetwork
import com.example.littlelemon.data.MenuNetwork
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val database by lazy {
        /*Room.databaseBuilder(
            applicationContext,
            MenuDatabase::class.java,
            "menu.db"
        ).build()*/
        MenuDatabase.getDatabase(applicationContext)
    }

    //private val database = MenuDatabase.getDatabase(applicationContext)

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = this.getSharedPreferences("LittleLemon.prefs", MODE_PRIVATE)

        lifecycleScope.launch {
            val menuItems = getMenu()

            withContext(Dispatchers.IO) {
                menuItems.map {
                    database.menuDao().saveMenuItem(it.toMenuItem())
                }
            }
            /*runOnUiThread {
                menuItemsLiveData.value = menuItems
            }*/
        }
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyNavigation(navController, sharedPreferences)
                }
            }
        }
    }

    private suspend fun getMenu(): List<MenuItemNetwork> {
        val response: MenuNetwork =
            client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body()
        return response.menu ?: listOf()
    }
}
