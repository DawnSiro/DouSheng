package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.PlayDao
import com.qxy.dousheng.model.video.PlayItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [PlayItem::class], version = 2, exportSchema = false)
abstract class PlayDatabase : RoomDatabase() {
    companion object {
        private lateinit var database: PlayDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): PlayDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    PlayDatabase::class.java,
                    "play_database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): PlayDao
}