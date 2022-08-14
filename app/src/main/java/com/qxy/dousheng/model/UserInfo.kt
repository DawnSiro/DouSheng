package com.qxy.dousheng.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "nation")
    val nation: String,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "profile")
    val profile: String)