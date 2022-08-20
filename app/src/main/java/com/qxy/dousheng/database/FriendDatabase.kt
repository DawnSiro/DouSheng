package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.FriendDao
import com.qxy.dousheng.model.FriendItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [FriendItem::class], version = 2, exportSchema = false)
abstract class FriendDatabase : RoomDatabase() {
    companion object {
        private lateinit var database: FriendDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): FriendDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    FriendDatabase::class.java,
                    "Friend_Database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): FriendDao
}