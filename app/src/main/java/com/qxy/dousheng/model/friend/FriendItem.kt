package com.qxy.dousheng.model.friend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据实体类，对应数据库中的表
 */
@Entity
data class FriendItem(
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "gender")
    val gender: Int,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "province")
    val province: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "open_id")
    val open_id: String,
    @ColumnInfo(name = "is_follow")
    var isFollow: Int, // isFollow:1 isFans:2 mutualConcern:3 non:0
    @ColumnInfo(name = "flag")
    val flag: Int  // 标志位 fans:1 follow:2 non:0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}