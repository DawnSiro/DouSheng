package com.qxy.dousheng.model.rank

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RankVersion 榜单版本类
 * 数据实体类，对应数据库中的表
 */
@Entity
data class RankVersionItem(
    @ColumnInfo(name = "active_time")
    val activeTime: String,
    @ColumnInfo(name = "version")
    val version: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}