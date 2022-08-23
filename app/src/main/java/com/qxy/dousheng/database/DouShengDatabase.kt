package com.qxy.dousheng.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.dousheng.dao.FriendDao
import com.qxy.dousheng.dao.InfoDao
import com.qxy.dousheng.dao.RankDao
import com.qxy.dousheng.dao.VideoDao
import com.qxy.dousheng.model.friend.FriendItem
import com.qxy.dousheng.model.info.InfoItem
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.model.video.VideoItem
import org.jetbrains.annotations.NotNull

/**
 * room 数据库对象，采用了单例模式实现
 */
@Database(entities = [FriendItem::class, InfoItem::class, RankItem::class, VideoItem::class], version = 2, exportSchema = false)
abstract class DouShengDatabase : RoomDatabase() {
    companion object {
        private lateinit var database: DouShengDatabase

        @JvmStatic
        @Synchronized
        fun getDatabase(@NotNull context: Context): DouShengDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    DouShengDatabase::class.java,
                    "database"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }

    abstract fun getFriendItemDao(): FriendDao

    abstract fun getInfoItemDao(): InfoDao

    abstract fun getRankItemDao(): RankDao

    abstract fun getVideoItemDao(): VideoDao
}