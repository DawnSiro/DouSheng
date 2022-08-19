package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.FollowDao
import com.qxy.dousheng.model.FansItem
import com.qxy.dousheng.model.FollowItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [FollowItem::class, FansItem::class], version = 1, exportSchema = false)
abstract class FollowDatabase : RoomDatabase() {

    companion object {
        private lateinit var database: FollowDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): FollowDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    FollowDatabase::class.java,
                    "database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getFollowDao(): FollowDao

}
