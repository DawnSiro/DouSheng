package com.qxy.dousheng.model.rank

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据实体类，对应数据库中的表
 */
@Entity
data class RankItem(
    val name: String,
    val poster: String,
    val time: String,
    val hot: Int,
    val type: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}