package com.qxy.dousheng.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "video")
data class Video(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "create_time")
    val createTime: String,
    @ColumnInfo(name = "cover")
    val cover: String,
    @ColumnInfo(name = "is_top")
    val isTop: Boolean,
    @ColumnInfo(name = "commentLevel")
    val commentLevel: String,
    @ColumnInfo(name = "playLevel")
    val playLevel: String)