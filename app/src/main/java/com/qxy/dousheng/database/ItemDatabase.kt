package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.FollowDao
import com.qxy.dousheng.dao.RankDao
import com.qxy.dousheng.model.FansItem
import com.qxy.dousheng.model.FollowItem
import com.qxy.dousheng.model.RankItem
import org.jetbrains.annotations.NotNull

//singleton
@Database(entities = [RankItem::class, FollowItem::class, FansItem::class], version = 5, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    companion object {
        private lateinit var database: ItemDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): ItemDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getItemDao(): RankDao

    abstract fun getFollowDao(): FollowDao

}
