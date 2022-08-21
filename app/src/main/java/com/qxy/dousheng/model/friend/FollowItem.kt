package com.qxy.dousheng.model.friend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FollowItem(
    // 这里是构造方法需要传入的参数
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
    @ColumnInfo(name = "is_follow")
    val isFollow: Boolean,
) {
    // 无需传入
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // 不设置为 0 会报异常
}