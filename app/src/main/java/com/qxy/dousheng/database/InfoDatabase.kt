package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.InfoDao
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [InfoDatabase::class], version = 1, exportSchema = false)
abstract class InfoDatabase : RoomDatabase() {
    companion object {
        private lateinit var database: InfoDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): InfoDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    InfoDatabase::class.java,
                    "Info_Database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): InfoDao
}