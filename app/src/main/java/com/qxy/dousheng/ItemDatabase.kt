package com.qxy.dousheng

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [MovieItem::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    companion object {
        private lateinit var database: ItemDatabase

        @JvmStatic
        fun getDatabase(@NotNull context: Context): ItemDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "movie_database"
                ).build()
            }
            return database
        }
    }

    abstract fun getItemDao(): MovieDao
}
