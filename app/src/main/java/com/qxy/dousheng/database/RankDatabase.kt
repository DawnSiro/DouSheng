package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.RankDao
import com.qxy.dousheng.model.RankItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [RankItem::class], version = 1, exportSchema = false)
abstract class RankDatabase : RoomDatabase() {

    companion object {
        private lateinit var database: RankDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): RankDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    RankDatabase::class.java,
                    "Rank_Database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): RankDao
}
