package com.example.littlelemon.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [MenuItem::class], version = 1, exportSchema = false)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDatabase? = null

        fun getDatabase(context: Context): MenuDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "menu.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

@Entity
data class MenuItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuDao {
    @Query("SELECT * FROM MenuItem")
    fun getAllMenuItems(): LiveData<List<MenuItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMenuItem(menuItem: MenuItem)

    @Delete
    fun deleteMenuItem(menuItem: MenuItem)
}

