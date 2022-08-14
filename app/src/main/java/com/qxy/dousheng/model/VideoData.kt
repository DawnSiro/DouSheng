package com.qxy.dousheng.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "video_data")
data class VideoData(@PrimaryKey(autoGenerate = true)
                     val id :Int,
                     @ColumnInfo(name = "title")
                     val title: String,
                     @ColumnInfo(name = "cover")
                     val cover: String,
                     @ColumnInfo(name = "data")
                     val data: String,
                     @ColumnInfo(name = "commentLevel")
                     val commentLevel: String,
                     @ColumnInfo(name = "likeLevel")
                     val likeLevel: String,
                     @ColumnInfo(name = "downloadLevel")
                     val downloadLevel: String,
                     @ColumnInfo(name = "forwardLevel")
                     val forwardLevel: String,
                     @ColumnInfo(name = "playLevel")
                     val playLevel: String,
                     @ColumnInfo(name = "shareLevel")
                     val shareLevel: String)