package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.VideoDao
import com.qxy.dousheng.model.VideoItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [VideoItem::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    companion object {
        private lateinit var database: VideoDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): VideoDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "Video_Database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): VideoDao
}